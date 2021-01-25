package com.jejujg.controller;

import com.jejujg.payload.request.ReplyRequest;
import com.jejujg.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;

//    @GetMapping("/{boardId}/{page}")
//    public ResponseEntity<?> replyListGET(@PathVariable("boardId") Long bid, @PathVariable("page") Integer page) {
//
//    }
//
//    @GetMapping("/{replyId}")
//    public ResponseEntity<?> replyGET(@PathVariable("replyId") Long rid) {
//
//    }
//
//    @PostMapping("/new")
//    public ResponseEntity<?> replyPOST(@RequestBody ReplyRequest replyRequest) {
//
//    }
//
//    @PutMapping("/{replyId}")
//    public ResponseEntity<?> replyPUT(@PathVariable("replyId") Long rid, @RequestBody ReplyRequest replyRequest) {
//
//    }
//
//    @DeleteMapping("/{replyId}")
//    public ResponseEntity<?> replyDEL(@PathVariable("replyId") Long rid) {
//
//    }
}
