package com.jejujg.controller;

import com.jejujg.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UploadController {

    @Value("${spring.uploadFolderPath}")
    private String uploadPath;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private final UploadService uploadService;

    @Secured("ROLE_USER")
    @PostMapping(value = "/upload/goods")
    public ResponseEntity<?> imagePOST(@RequestPart MultipartFile file, String categoryNum) {
        Map<String, Object> map = uploadService.uploadGoodsImage(file, categoryNum);
        String errorMessage = "error";

        if (map.get("isImage").equals(true)){
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else {
            errorMessage = "not image";
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> imageGET(@RequestParam("imageName") String imageName){
        logger.info("파일 경로: " + uploadPath + "/" + imageName);
        File file = new File(uploadPath + "/" + imageName);

        ResponseEntity<byte[]> result = null;

        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        }catch (AccessDeniedException e){
            logger.error("잘못된 접근입니다.");
        }catch (NoSuchFileException e){
            logger.error("존재하지 않는 파일입니다.");
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
}
