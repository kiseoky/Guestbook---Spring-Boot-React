package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceImplTest {
    @Autowired
    GuestbookService guestbookService;

    @Test
    public void register() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample title")
                .content("content test")
                .writer("test writer")
                .build();

        System.out.println("guestbookService = " + guestbookService.register(guestbookDTO));
    }

    @Test
    public void list() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.list(pageRequestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println("guestbookDTO = " + guestbookDTO);
        }

        System.out.println("resultDTO.getTotalPage()) = " + resultDTO.getTotalPage());
        for (Integer pageList : resultDTO.getPageList()) {
            System.out.println("pageList = " + pageList);
        }
    }

    @Test
    public void search() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("title")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> list = guestbookService.list(pageRequestDTO);

        System.out.println("list.getDtoList() = " + list.getDtoList());
    }


}