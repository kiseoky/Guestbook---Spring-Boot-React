package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.entity.Reply;
import com.example.guestbook.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/reply")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@Transactional
public class ReplyController {
    private final ReplyRepository replyRepository;

    @GetMapping("")
    public Page<Reply> list() {

        List<Reply> replies = replyRepository.findAll();

        return getReplies(replies);
    }
    @GetMapping("/{id}")
    public Page<Reply> findByGuestbookId(@PathVariable Long id) {
        List<Reply> replies = replyRepository.findByGuestbookId(id);

        return getReplies(replies);
    }

    private Page<Reply> getReplies(List<Reply> replyList) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), replyList.size());
        return new PageImpl<>(replyList.subList(start, end), pageable, replyList.size());
    }
}
