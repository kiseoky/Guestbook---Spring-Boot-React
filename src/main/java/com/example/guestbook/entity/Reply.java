package com.example.guestbook.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"writer", "guestbook"})
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestbook_id")
    private Guestbook guestbook;

    public void setWriter(Member writer) {
        this.writer = writer;
        writer.getReplies().add(this);
    }
    public void setGuestbook(Guestbook guestbook) {
        this.guestbook = guestbook;
        guestbook.getReplies().add(this);
    }
}
