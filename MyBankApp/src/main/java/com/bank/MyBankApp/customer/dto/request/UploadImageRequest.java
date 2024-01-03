package com.bank.MyBankApp.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadImageRequest {
    @NotNull(message = "field customer id cannot be null")
    private Integer customerId;
    @NotNull(message = "field profile image cannot be null")
    private MultipartFile profileImage;
}
