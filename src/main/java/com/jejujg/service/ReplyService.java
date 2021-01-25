package com.jejujg.service;

import com.jejujg.mapper.ReplyMapper;
import com.jejujg.payload.request.ReplyRequest;
import com.jejujg.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;

    public void list() {

    }

    public void read() {

    }

    public void save(ReplyRequest replyRequest) {
        replyRepository.save(replyMapper.replyDtoToEntity(replyRequest));
    }

    public void update() {

    }

    public void delete() {

    }
}
