package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
public class Movies_has_participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 영화&영화인 ID

    @ManyToOne
    @JoinColumn(name = "movies_movieid")
    private Movies moiveId; // 영화 코드

    @ManyToOne
    @JoinColumn(name = "participants_peopleid")
    private Participants peopleId;  // 영화인 코드

    @Column
    private String moviePartNm; // 참여 분야

    @Builder
    public Movies_has_participants(Long id, Movies moiveId, Participants peopleId, String moviePartNm) {
        this.id = id;
        this.moiveId = moiveId;
        this.peopleId = peopleId;
        this.moviePartNm = moviePartNm;
    }
}
