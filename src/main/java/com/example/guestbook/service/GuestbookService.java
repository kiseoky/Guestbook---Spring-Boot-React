package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO guestbookDTO);

    GuestbookDTO read(Long id);

    void remove(Long id);

    void modify(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> list(PageRequestDTO pageRequestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook guestbook) {
        GuestbookDTO guestbookDto = GuestbookDTO.builder()
                .id(guestbook.getId())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .writer(guestbook.getWriter())
                .createdAt(guestbook.getCreatedAt())
                .updatedAt(guestbook.getUpdatedAt())
                .build();
        return guestbookDto;
    }
}
