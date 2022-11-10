package com.example.SWExhibition.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Movies_has_participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 영화&영화인 ID

    @ManyToOne
    @JoinColumn(name = "movies_id")
    private Movies moiveId; // 영화 코드

    @ManyToOne
    @JoinColumn(name = "participants_peopleId")
    private Participants peopleId;  // 영화인 코드

    @Builder
    public Movies_has_participants(Long id, Movies moiveId, Participants peopleId) {
        this.id = id;
        this.moiveId = moiveId;
        this.peopleId = peopleId;
    }
}
