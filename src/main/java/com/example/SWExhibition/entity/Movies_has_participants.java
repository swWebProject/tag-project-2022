package com.example.SWExhibition.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Movies_has_participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moviesParticipantsId;  // 영화&영화인 ID

    @ManyToOne
    @JoinColumn(name = "movies_moiveCd")
    private Movies moiveCd; // 영화 코드

    @ManyToOne
    @JoinColumn(name = "participants_peopleCd")
    private Participants peopleCd;  // 영화인 코드
}
