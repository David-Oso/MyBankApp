package com.bank.MyBankApp.appSecurity;

import com.bank.MyBankApp.appSecurity.jwtToken.MyBankJwtTokenRepository;
import com.bank.MyBankApp.appSecurity.service.JwtService;
import com.bank.MyBankApp.appSecurity.service.MyBankUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@AllArgsConstructor
public class MyBankAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MyBankUserDetailsService userDetailsService;
    private final MyBankJwtTokenRepository tokenRepository;
    
    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        validateBearer(request);
        filterChain.doFilter(request, response);
    }

    private void validateBearer(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String bearer = "Bearer ";
        if(StringUtils.hasText(authHeader) &&
            StringUtils.startsWithIgnoreCase(authHeader, bearer)) {
            final String jwtToken = authHeader.substring(bearer.length());
            final String userEmail = jwtService.extractUsername(jwtToken);
            validateToken(request, userEmail, jwtToken);
        }
    }

    private void validateToken(HttpServletRequest request, String userEmail, String jwtToken) {
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            setAuthentication(request, userEmail, jwtToken);
        }
    }

    private void setAuthentication(HttpServletRequest request, String userEmail, String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        boolean isTokenValid = isTokenValid(jwtToken);
        if(jwtService.isValidToken(jwtToken, userEmail) && isTokenValid){
            final UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    private boolean isTokenValid(String jwtToken) {
        return tokenRepository.findByAccessToken(jwtToken)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);
    }
}
