package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.MovieDto;
import com.example.SWExhibition.dto.NaverMovieDto;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MoviesService {

    private final NaverMoviesService naverMoviesService;
    private final MoviesRepository moviesRepository;
    private final RestTemplate restTemplate;    // rest 방식 api 호출

    private final String movieUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?"; // 영화 진흥 위원회 영화 목록 api url
    private final String apiKey = "key=4801347d5095e67e89bbba73e7650760";   // api 키 값

    // 영화 정보 불러오기
    public List<MovieDto> movieInfo(String movieNm) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = new StringBuilder(movieUrl)
                .append(apiKey)
                .append("&movieNm=")
                .append(movieNm)
                .toString();

        // 영화 목록 api를 결과를 String 형태로 받음
        String data = restTemplate.getForObject(url, String.class);
        log.info(data);

        // 응답 값 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        // 가장 큰 객체인 movieListResult 파싱
        JSONObject jsonMovieListResult = (JSONObject) jsonObject.get("movieListResult");

        // 영화 정보를 배열로 담은 movieList 파싱
        JSONArray jsonMovieList = (JSONArray) jsonMovieListResult.get("movieList");
        log.info(jsonMovieList.toString());

        List<MovieDto> movieDtoList = new ArrayList<>();  // 필요한 데이터만 담은 Dto List

        // JSON 객체를 Dto로 변환하고 List에 추가
        for (Object o : jsonMovieList) {
            JSONObject item = (JSONObject) o;

            StringBuilder sb = new StringBuilder();
            // 영화 감독 정보를 배열로 담은 directors 파싱
            JSONArray jsonDirectors = (JSONArray) item.get("directors");
            for (Object ob : jsonDirectors) {
                JSONObject director = (JSONObject) ob;

                sb.append((String) director.get("peopleNm"));
                sb.append("|"); // 구분
            }

            String directors = sb.toString();   // 배열 -> String

            movieDtoList.add(toDto(item, directors));
            log.info(toDto(item, directors).toString());
        }

        return movieDtoList;
    }

    // 해당 영화를 Movies Table에 저장
    @Transactional
    public void save(String movieNm) throws ParseException {
        List<NaverMovieDto> naverMovieDtoList = naverMoviesService.naverMovieInfo(movieNm); // naver api로 받아온 정보들
        List<MovieDto> movieDtoList = movieInfo(movieNm);   // 영화진흥위원회 api로 받아온 정보들

        // 두 api의 결과값으로 같은 영화를 찾음
        for (MovieDto ob : movieDtoList) {
            for (NaverMovieDto o : naverMovieDtoList) {
                // 감독명, 영어 제목 또는 원제 비교 해서 같으면
                if (o.getDirector().equals(ob.getDirectors()) && o.getSubtitle().equals(ob.getMovieNmEn()) && !moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                    // 두 Dto를 하나의 Entity로 변환
                    Movies entity = ob.toEntity(o);
                    log.info(entity.toString());

                    // 저장
                    moviesRepository.save(entity);
                }
            }
            if (!moviesRepository.existsByMovieCd(ob.getMovieCd()))
                moviesRepository.save(ob.toEntity());   // 포스터가 없음
        }
    }

    // 주어진 이름과 관련있는 영화 리스트를 반환
    @Transactional
   public List<Movies> search(String movieNm) throws ParseException {
       List<MovieDto> searchList = movieInfo(movieNm); // 영화진흥위원회 api 결과값

       // Dto 정보로 DB에서 데이터 가져오기
       List<Movies> resultList = new ArrayList<>();
        for (MovieDto o : searchList) {
            // DB에 있으면 불러오기
            if (moviesRepository.existsByMovieCd(o.getMovieCd())) {
                Movies entity = moviesRepository.findByMovieCd(o.getMovieCd());
                resultList.add(entity);
                log.info(entity.toString());
            }
            // DB에 없으면 저장하고 불러오기
            else {
                save(o.getMovieNm());
                Movies entity = moviesRepository.findByMovieCd(o.getMovieCd());
                log.info(entity.toString());
                resultList.add(entity);
            }
        }

       return resultList;
    }

    // JSONObject -> Dto, 필요한 데이터만 담음
    private MovieDto toDto(JSONObject item, String directors) {
        return MovieDto.builder()
                .movieCd((String) item.get("movieCd"))
                .movieNm((String) item.get("movieNm"))
                .movieNmEn((String) item.get("movieNmEn"))
                .nationAlt((String) item.get("nationAlt"))
                .openDt((String) item.get("openDt"))
                .genreAlt((String) item.get("genreAlt"))
                .directors(directors)
                .build();
    }
}
