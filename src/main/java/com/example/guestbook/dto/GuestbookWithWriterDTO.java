package com.example.guestbook.dto;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import lombok.*;

@Data
@AllArgsConstructor
public class GuestbookWithWriterDTO {
    private Guestbook guestbook;
    private Member writer;
}

