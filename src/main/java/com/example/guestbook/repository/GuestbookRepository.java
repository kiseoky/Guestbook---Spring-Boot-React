package com.example.guestbook.repository;

import com.example.guestbook.dto.GuestbookWithReplyCountDTO;
import com.example.guestbook.dto.GuestbookWithWriterDTO;
import com.example.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
    @Query("select new com.example.guestbook.dto.GuestbookWithWriterDTO(g, w) from Guestbook g left join g.writer w where g.id = :guestbook_id")
    List<GuestbookWithWriterDTO> getGuestbookWithWriter(@Param("guestbook_id") Long id);



    @Query("select g, r from Guestbook g left join Reply r on g = r.guestbook where g.id =:guestbook_id")
    List<Object[]> getGuestbookWithReply(@Param("guestbook_id") Long id);

    @Query(value="select new com.example.guestbook.dto.GuestbookWithReplyCountDTO(g, w, count(r))" +
            " from Guestbook g " +
            " left join g.writer w " +
            " left join Reply r on r.guestbook = g " +
            " group by g ", countQuery = "select count(g) from Guestbook g")
    Page<GuestbookWithReplyCountDTO> getGuestbookWithReplyCount(Pageable pageable);

    @Query("select new com.example.guestbook.dto.GuestbookWithReplyCountDTO(g, w, count(r))" +
            " from Guestbook g " +
            " left join g.writer w " +
            " left join Reply r on r.guestbook = g " +
            " where g.id = :guestbook_id")
    List<GuestbookWithReplyCountDTO> getGuestbookById(@Param("guestbook_id") Long id);

}
