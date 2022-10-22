package com.example.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "writer")
public class Guestbook extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "guestbook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String title;

    private String content;

    public void updateGuestbook(String title, String content){
        this.title = title;
        this.content = content;
    }
}
