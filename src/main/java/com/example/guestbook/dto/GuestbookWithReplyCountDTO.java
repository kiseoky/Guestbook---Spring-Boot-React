package com.example.guestbook.dto;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestbookWithReplyCountDTO {
    private Guestbook guestbook;
    private Member writer;
    private Long replyCount;
}
