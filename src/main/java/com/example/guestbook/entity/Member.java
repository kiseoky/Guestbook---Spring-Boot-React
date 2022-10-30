package com.example.guestbook.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @OneToMany(mappedBy = "writer")
    private List<Guestbook> guestbooks = new ArrayList<Guestbook>();

    @OneToMany(mappedBy = "writer")
    private List<Reply> replies = new ArrayList<Reply>();

    public void addGuestbook(Guestbook guestbook) {
        guestbook.setWriter(this);
        this.guestbooks.add(guestbook);
    }
    public void addReply(Reply reply) {
        reply.setWriter(this);
        this.replies.add(reply);
    }
}
