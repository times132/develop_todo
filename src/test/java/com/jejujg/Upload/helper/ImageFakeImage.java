package com.jejujg.Upload.helper;

import com.jejujg.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

public class ImageFakeImage {

    @Autowired
    protected UploadService uploadService;

    protected MockMultipartFile IMAGE_FILE;
    protected MockMultipartFile FAKE_IMAGE_FILE;

    protected void prepareImageNotImage() throws IOException {
        FileInputStream fis = new FileInputStream("D:\\test\\image1.jpg");
        this.IMAGE_FILE = new MockMultipartFile("image", fis);

        fis = new FileInputStream("D:\\test\\text1.jpg");
        this.FAKE_IMAGE_FILE = new MockMultipartFile("text1", fis);
    }
}
