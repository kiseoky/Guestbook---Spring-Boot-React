package com.example.guestbook.controller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class GuestBookController {
    private final GuestbookService guestbookService;

    @GetMapping({"/list"})
    public PageResultDTO<GuestbookDTO, Guestbook> list(PageRequestDTO pageRequestDTO) {
        return guestbookService.list(pageRequestDTO);
    }

    @GetMapping("/{id}")
    public GuestbookDTO read(@PathVariable Long id) {
        return guestbookService.read(id);
    }


    @PostMapping("/register")
    public void registerPost(GuestbookDTO dto) {
        System.out.println("dto = " + dto);
        guestbookService.register(dto);
    }

    @PostMapping("/remove")
    public void removePost(Long id) {
        guestbookService.remove(id);
    }

    @PostMapping("/modify")
    public void modifyPost(GuestbookDTO dto) {
        guestbookService.modify(dto);
    }
}
