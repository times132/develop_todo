package com.jejujg;

import com.jejujg.model.Free;
import com.jejujg.model.Img;
import com.jejujg.model.Notice;
import com.jejujg.repository.FreeRepository;
import com.jejujg.repository.ImgRepository;
import com.jejujg.repository.NoticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
public class ImgFreeTest {

    @Autowired
    private ImgRepository imgRepository;
    @Autowired
    private FreeRepository freeRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeEach
    void before() {
        imgRepository.deleteAll();
        freeRepository.deleteAll();
        noticeRepository.deleteAll();
    }

    @Test
    void test_1() {
        Free free = new Free();
        free.setName("자유게시판");
        Long freeId = freeRepository.save(free).getId();

        Notice notice = new Notice();
        notice.setName("공지게시판");
        Long noticeId = noticeRepository.save(notice).getId();

        Img img = new Img();
        img.setName("자유게시판 이미지1");
        img.setFree(free);
        imgRepository.save(img);

        Img img2 = new Img();
        img2.setName("자유게시판 이미지2");
        img2.setFree(free);
        imgRepository.save(img2);

        Img img3 = new Img();
        img3.setName("공지게시판 이미지1");
        img3.setNotice(notice);
        imgRepository.save(img3);

        for (Img image : freeRepository.findById(freeId).get().getImgList()) {
            System.out.println("이미지 이름 : " + image.getName());
            assertEquals("자유게시판", image.getFree().getName());
        }

        for (Img image : noticeRepository.findById(noticeId).get().getImgList()) {
            System.out.println("이미지 이름 : " + image.getName());
            assertEquals("공지게시판", image.getNotice().getName());
        }
    }
}
