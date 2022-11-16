package com.example.SWExhibition.dto;

import com.example.SWExhibition.entity.Movies;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class MovieDto {

    private Long id;    // id
    private String movieCd; // 영화 코드
    private String movieNm; // 영화 이름
    private String movieNmEn; // 영화 영어 제목
    private String openDt;  // 개봉일
    private String nationAlt;   // 제작 국가
    private String genreAlt;    // 장르, ','으로 구분
    private String directors;    // 감독명, '|'으로 감독명 구분
    private List<CommentResponseDto> comments;

    @Builder
    public MovieDto(String movieCd, String movieNm, String movieNmEn, String openDt, String nationAlt, String genreAlt, String directors) {
        this.movieCd = movieCd;
        this.movieNm = movieNm;
        this.movieNmEn = movieNmEn;
        this.nationAlt = nationAlt;
        this.genreAlt = genreAlt;

        // "" 값이 들어오면 저장 안함 -> null
        if (!openDt.equals(""))
            this.openDt = openDt;

        // "" 값이 들어오면 저장 안함 -> null
        if (!directors.equals(""))
            this.directors = directors;
    }

    // Dto -> Entity
    public Movies toEntity(NaverMovieDto naverMovieDto) {
        return Movies.builder()
                .id(id)
                .movieCd(movieCd)
                .movieNm(movieNm)
                .movieNmEn(movieNmEn)
                .poster(naverMovieDto.getImage())
                .nationAlt(nationAlt)
                .openDt(openDt)
                .build();
    }

    // 영화진흥위원회 api에만 있는 영화, 포스터는 없음 (대체 사진)
    // Dto -> Entity
    public Movies toEntity() {
        return Movies.builder()
                .id(id)
                .movieCd(movieCd)
                .movieNm(movieNm)
                .movieNmEn(movieNmEn)
                .nationAlt(nationAlt)
                .openDt(openDt)
                .poster("https://velog.velcdn.com/images/johnbl030102/post/60146e6e-8481-432f-9de6-dd1a7534684a/image.png")
                .build();
    }


}



