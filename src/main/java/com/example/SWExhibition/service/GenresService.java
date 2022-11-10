package com.example.SWExhibition.service;

import com.example.SWExhibition.entity.Genres;
import com.example.SWExhibition.repository.GenresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class GenresService {

    private final GenresRepository genresRepository;

    // MovieDto로 받은 장르를 저장
    @Transactional
    public Genres saveGenre(String genre) {
        // Genre DB에 없으면 저장
        if (!genresRepository.existsByGenreAlt(genre)) {
            Genres entity = toEntity(genre);
            log.info(entity.toString());

            return genresRepository.save(entity);
        }

        return genresRepository.findByGenreAlt(genre);
    }

    // MovieDto -> Genres Entity
    public Genres toEntity(String genre) {
        return Genres.builder()
                .genreAlt(genre)
                .build();
    }
}
