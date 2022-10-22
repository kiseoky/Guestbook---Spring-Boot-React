package com.example.guestbook.repository;


import com.example.guestbook.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying
    @Query("delete from Reply r where r.guestbook.id = :guestbookId")
    void removeByGuestbookId(@Param("guestbookId")Long guestbookId);

    @Query("select r from Reply r where r.guestbook.id = :guestbookId")
    List<Reply> findByGuestbookId(@Param("guestbookId")Long guestbookId);
}
