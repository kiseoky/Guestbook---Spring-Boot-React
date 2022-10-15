package com.example.guestbook.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestbookDTO  {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt, updatedAt;
}
