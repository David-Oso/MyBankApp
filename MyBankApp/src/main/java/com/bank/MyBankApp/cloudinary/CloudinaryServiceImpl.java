package com.bank.MyBankApp.cloudinary;

import com.bank.MyBankApp.exception.ImageUploadException;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService{
    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile image) {
        try{
            Map<?, ?> response =  cloudinary.uploader()
                    .upload(image.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()));
            log.info("\n:::::::::::::::::::::::::::::: IMAGE UPLOADED SUCCESSFULLY   ::::::::::::::::::::::::::::::");
            return response.get("url").toString();
        }catch (IOException ioException){
            throw new ImageUploadException("Error uploading image");
        }
    }
}

