package com.bank.MyBankApp.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String upload(MultipartFile file);
}
