package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    GuestbookRepository guestbookRepository;
    
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i->{
            Guestbook guestbook = Guestbook.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer("User " + i)
                    .build();
            System.out.println("guestbookRepository.save(guestbook) = " + guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void update(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.updateGuestbook("타이틀", "컨텐츠");
            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void querydslContainsKeyword(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println("guestbook = " + guestbook);
        });
    }

    @Test
    public void querydslContainsKeywordAndIdGt(){
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";

        BooleanExpression titleContains = qGuestbook.title.contains(keyword);
        BooleanExpression contentContains = qGuestbook.content.contains(keyword);
        BooleanExpression all = titleContains.or(contentContains);

        builder.and(all);
        builder.and(qGuestbook.id.gt(280L));

        Iterable<Guestbook> result = guestbookRepository.findAll(builder);

        result.forEach(guestbook -> {
            System.out.println("guestbook = " + guestbook);
        });
    }
}