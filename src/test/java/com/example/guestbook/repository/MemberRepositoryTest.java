package com.example.guestbook.repository;

import com.example.guestbook.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void insertMembers(){

        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .name("user" + i)
                    .password("1111")
                    .build();
            memberRepository.save(member);
        });

    }
}