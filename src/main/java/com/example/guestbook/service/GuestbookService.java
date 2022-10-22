package com.example.guestbook.service;

import com.example.guestbook.dto.*;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import org.springframework.transaction.annotation.Transactional;

public interface GuestbookService {
    Long register(GuestbookDTO guestbookDTO);

    GuestbookDTO get(Long id);

    void remove(Long id);

    void removeWithReplies(Long id);

    void modify(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, GuestbookWithReplyCountDTO> list(PageRequestDTO pageRequestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {

        Member writer = Member.builder().id(dto.getWriterId()).build();
        Guestbook entity = Guestbook.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(writer)
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook guestbook, Member writer, Long replyCount) {
        GuestbookDTO guestbookDto = GuestbookDTO.builder()
                .id(guestbook.getId())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .writerId(writer.getId())
                .writerEmail(writer.getEmail())
                .writerName(writer.getName())
                .createdAt(guestbook.getCreatedAt())
                .updatedAt(guestbook.getUpdatedAt())
                .replyCount(replyCount.intValue())
                .build();
        return guestbookDto;
    }
}
