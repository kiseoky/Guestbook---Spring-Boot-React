package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import com.example.guestbook.entity.Reply;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    GuestbookRepository guestbookRepository;

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 1000).forEach(i->{
            long boardId = (long)(Math.random()*900)+100;
            long writerId = (long)(Math.random()*100)+1;

            Optional<Guestbook> boardById = guestbookRepository.findById(boardId);
            Optional<Member> writerById = memberRepository.findById(writerId);

            if(!boardById.isPresent()){
                return;
//                throw new IllegalStateException("No guestbook Id in range 101~200");
            }
            if (writerById.isEmpty()) {
                return;
//                throw new IllegalStateException("No member Id in range 1~100");
            }
            Guestbook guestbook = boardById.get();
            Member writer = writerById.get();
            Reply reply = Reply.builder()
                    .guestbook(guestbook)
                    .writer(writer)
                    .text("reply..." + i)
                    .build();

            System.out.println("replyRepository.save(reply) = " + replyRepository.save(reply));
        });
    }

    @Test
    @Transactional
    public void testRead() {
        Optional<Reply> optional = replyRepository.findById(201L);
        if(optional.isEmpty()){
            return;
        }
        Reply reply = optional.get();
        System.out.println("reply = " + reply);
        System.out.println("reply.getGuestbook() = " + reply.getGuestbook());

    }
}