package com.example.guestbook.repository;

import com.example.guestbook.dto.GuestbookWithReplyCountDTO;
import com.example.guestbook.dto.GuestbookWithWriterDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    GuestbookRepository guestbookRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder()
                    .id((long) i).build();
            Guestbook guestbook = Guestbook.builder()
                    .title("Title " + i)
                    .content("Content " + i)
                    .writer(member)
                    .build();
            System.out.println("guestbookRepository.save(guestbook) = " + guestbookRepository.save(guestbook));
        });
    }
    
    @Test
    @Transactional
    public void testRead() {
        Optional<Guestbook> optional = guestbookRepository.findById(101L);
        if(optional.isEmpty()){
            return;
        }
        Guestbook guestbook = optional.get();
        System.out.println("guestbook = " + guestbook.getTitle());
        System.out.println("guestbook.getWriter() = " + guestbook.getWriter());
    }

    @Test
    @Transactional
    public void testReadWithWriter() {
        GuestbookWithWriterDTO guestbookWithWriterDTO = guestbookRepository.getGuestbookWithWriter(101L).get(0);

        System.out.println("guestbookWithWriter.getGuestbook() = " + guestbookWithWriterDTO.getGuestbook());
        System.out.println("guestbookWithWriter.getWriter() = " + guestbookWithWriterDTO.getWriter());
    }
    @Test
    public void testReadWithWriterOrigin() {
        Object guestbookWithWriter = guestbookRepository.getGuestbookWithWriter(101L);
        Object[] arr = (Object[]) guestbookWithWriter;

        System.out.println("arr[0] = " + (Guestbook)arr[0]);
        System.out.println("(Member)arr[1] = " + (Member) arr[1]);
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = guestbookRepository.getGuestbookWithReply(101L);
        for (Object[] arr : result) {
            System.out.println("Arrays.toString(arr) = " + Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount() {
        Pageable pageable =  PageRequest.of(0, 10, Sort.by("id").descending());
        Page<GuestbookWithReplyCountDTO> res = guestbookRepository.getGuestbookWithReplyCount(pageable);
        System.out.println("res.getContent().get(0).getGuestbook() = " + res.getContent().get(0));
    }

    @Test
    @Transactional
    public void readById() {
//        GuestbookWithWriterWithReplyCountDTO guestbookById = guestbookRepository.getGuestbookById(600L).get(0);
//        System.out.println("result = " + guestbookById);
    }

//    @Test
//    public void update(){
//        Optional<Guestbook> result = guestbookRepository.findById(300L);
//        if (result.isPresent()) {
//            Guestbook guestbook = result.get();
//
//            guestbook.updateGuestbook("타이틀", "컨텐츠");
//            guestbookRepository.save(guestbook);
//        }
//    }
//
//    @Test
//    public void querydslContainsKeyword(){
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        String keyword = "1";
//        BooleanBuilder builder = new BooleanBuilder();
//        BooleanExpression expression = qGuestbook.title.contains(keyword);
//        builder.and(expression);
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//
//        result.stream().forEach(guestbook -> {
//            System.out.println("guestbook = " + guestbook);
//        });
//    }
//
//    @Test
//    public void querydslContainsKeywordAndIdGt(){
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        BooleanBuilder builder = new BooleanBuilder();
//
//        String keyword = "1";
//
//        BooleanExpression titleContains = qGuestbook.title.contains(keyword);
//        BooleanExpression contentContains = qGuestbook.content.contains(keyword);
//        BooleanExpression all = titleContains.or(contentContains);
//
//        builder.and(all);
//        builder.and(qGuestbook.id.gt(280L));
//
//        Iterable<Guestbook> result = guestbookRepository.findAll(builder);
//
//        result.forEach(guestbook -> {
//            System.out.println("guestbook = " + guestbook);
//        });
//    }
}