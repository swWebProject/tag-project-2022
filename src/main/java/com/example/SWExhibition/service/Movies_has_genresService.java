package com.example.SWExhibition.service;

import com.example.SWExhibition.entity.Genres;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_genres;
import com.example.SWExhibition.repository.Movies_has_genresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class Movies_has_genresService {

    private final Movies_has_genresRepository movies_has_genresRepository;

    // 장르명을 하나의 문자열로 만듦
    public String getGenres(Movies movie) {
        List<Movies_has_genres> moviesHasGenresList = movies_has_genresRepository.findByMovieId(movie); // 영화와 매핑된 장르 값 불러오기
        log.info(moviesHasGenresList.toString());
        StringBuilder sb = new StringBuilder(); // 여러 장르를 하나의 문자열로 만들기

        for (int i = 0; i < moviesHasGenresList.size(); i++) {
            Genres genres = moviesHasGenresList.get(i).getGenreID();    // 영화에 매핑된 장르 정보 하나씩 가져오기
            sb.append(genres.getGenreAlt());    // 장르명 추가
            if (i < moviesHasGenresList.size() - 1)
                sb.append(", ");
        }

        return sb.toString();
    }
}
