package com.bank.MyBankApp.cloudinary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static com.bank.MyBankApp.utilities.MyBankAppUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CloudinaryServiceImplTest {
    @Autowired
    private CloudinaryService cloudinaryService;

    private MultipartFile uploadTestImageFile(String imageUrl){
        MultipartFile file = null;
        try{
            file =  new MockMultipartFile("test_upload_image",
                    new FileInputStream(imageUrl));
        }catch (IOException exception){
            System.out.println(exception.getMessage());
        }
        return file;
    }

    @Test
    void upload() {
        MultipartFile image = uploadTestImageFile(TEST_IMAGE_LOCATION);
        String uploadImage = cloudinaryService.upload(image);
        assertThat(uploadImage).isNotNull();
    }
}