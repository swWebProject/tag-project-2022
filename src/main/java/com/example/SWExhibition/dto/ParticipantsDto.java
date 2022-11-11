package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Participants;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ParticipantsDto {

    private Long id;
    private String peopleCd;  // 영화인 코드
    private String peopleNm;    // 이름
    private String peopleNmEn;  // 영어 이름
    private String sex; // 성별
    private String repRoleNm;   // 영화인 분류명 (배우 or 감독 or 촬영팀 등등)
    private List<FilmoInfo> filmoInfoList;    // 필모그래피 정보

    @Builder
    public ParticipantsDto(Long id, String peopleCd, String peopleNm, String peopleNmEn, String sex, String repRoleNm, List<FilmoInfo> filmoInfoList) {
        this.id = id;
        this.peopleCd = peopleCd;
        this.peopleNm = peopleNm;
        this.peopleNmEn = peopleNmEn;
        this.sex = sex;
        this.repRoleNm = repRoleNm;
        this.filmoInfoList = filmoInfoList;
    }

    // Dto -> Entity
    public Participants toEntity() {
        return Participants.builder()
                .id(id)
                .peopleCd(peopleCd)
                .peopleNm(peopleNm)
                .peopleNmEn(peopleNmEn)
                .sex(sex)
                .repRoleNm(repRoleNm)
                .build();
    }
}
