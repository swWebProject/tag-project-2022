package com.example.SWExhibition.entity;

import com.example.SWExhibition.security.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Table(name = "likes")
@Entity
public class Likes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comments comments;

    private PrincipalDetails users;

    public Likes(Comments comments, PrincipalDetails users) {
        this.comments = comments;
        this.users = users;

    }
}