package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.GuestbookWithReplyCountDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import com.example.guestbook.repository.GuestbookRepository;
import com.example.guestbook.repository.MemberRepository;
import com.example.guestbook.repository.ReplyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class GuestbookServiceImplTest {
    @Autowired
    GuestbookService guestbookService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    GuestbookRepository guestbookRepository;

    @Autowired
    ReplyRepository replyRepository;
    @Test
    public void register() {
        Optional<Member> memberOptional = memberRepository.findById(1L);
        if(memberOptional.isEmpty()){
            throw new IllegalStateException("No Member Id 1L.");
        }
        Member writer = memberOptional.get();
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample title")
                .content("content test")
                .writerId(writer.getId())
                .writerEmail(writer.getEmail())
                .writerName(writer.getName())
                .build();

        Long registerId = guestbookService.register(guestbookDTO);


        assertThat(registerId).isEqualTo(guestbookRepository.findById(registerId).get().getId());
    }

    @Test
    public void list() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<GuestbookDTO, GuestbookWithReplyCountDTO> resultDTO = guestbookService.list(pageRequestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println("guestbookDTO = " + guestbookDTO);
        }

        System.out.println("resultDTO.getTotalPage()) = " + resultDTO.getTotalPage());
        for (Integer pageList : resultDTO.getPageList()) {
            System.out.println("pageList = " + pageList);
        }
    }

    @Test
    public void get() {
        GuestbookDTO guestbookDTO = guestbookService.get(501L);
        System.out.println("guestbookDTO = " + guestbookDTO);
    }

    @Test
//    @Rollback
//    @Commit
    public void removeWithReplies() {

        Long id = 595L;
        guestbookService.removeWithReplies(id);

        assertThat(guestbookRepository.findById(id)).isEmpty();
        assertThat(replyRepository.findByGuestbookId(id)).isEmpty();
    }
    @Test
    public void search() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .type("tc")
//                .keyword("title")
//                .build();
//
//        PageResultDTO<GuestbookDTO, Guestbook> list = guestbookService.list(pageRequestDTO);
//
//        System.out.println("list.getDtoList() = " + list.getDtoList());
    }


}