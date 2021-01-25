package com.jejujg.Upload.helper;

import com.jejujg.repository.UploadRepository;
import com.jejujg.service.UploadService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@AllArgsConstructor
public class UploadTestHelper {

    private UploadService uploadService;
    private UploadRepository uploadRepository;

//    public UploadTestHelper(UploadService uploadService, UploadRepository uploadRepository) {
//        this.uploadService = uploadService;
//        this.uploadRepository = uploadRepository;
//
//    }

    protected MultipartFile createImageMultipartFile() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\test\\image1.jpg");
        return new MockMultipartFile("image", fis);
    }

    protected MultipartFile createFakeImageMultipartFile() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\test\\text1.jpg");
        return new MockMultipartFile("text", fis);
    }
}
