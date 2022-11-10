package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.MovieDto;
import com.example.SWExhibition.dto.NaverMovieDto;
import com.example.SWExhibition.entity.Genres;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.entity.Movies_has_genres;
import com.example.SWExhibition.repository.GenresRepository;
import com.example.SWExhibition.repository.MoviesRepository;
import com.example.SWExhibition.repository.Movies_has_genresRepository;
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
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Service
@Slf4j
public class MoviesService {

    private final NaverMoviesService naverMoviesService;
    private final MoviesRepository moviesRepository;
    private final GenresService genresService;
    private final GenresRepository genresRepository;
    private final Movies_has_genresRepository movies_has_genresRepository;
    private final RestTemplate restTemplate;    // rest 방식 api 호출

    private final String movieDetailedUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";  // 영화진흥위원회 영화 상세정보 api url

    private final String movieUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?"; // 영화진흥위원회 영화 목록 api url
    private final String apiKey = "key=4801347d5095e67e89bbba73e7650760";   // api 키 값

    // 영화 목록
    // 영화 정보 불러오기
    @Transactional(readOnly = true)
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

            String directors = sb.toString();

            movieDtoList.add(toDto(item, directors));
            log.info(toDto(item, directors).toString());
        }

        return movieDtoList;
    }

    // 영화 상세정보
    // 영화 정보 불러오기
    @Transactional(readOnly = true)
    public MovieDto movieDetailedInfo(String movieCd) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = new StringBuilder(movieDetailedUrl)
                .append(apiKey)
                .append("&movieCd=")
                .append(movieCd)
                .toString();

        // 영화 목록 api를 결과를 String 형태로 받음
        String data = restTemplate.getForObject(url, String.class);
        log.info(data);

        // 응답 값 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        // 가장 큰 객체인 movieListResult 파싱
        JSONObject jsonMovieInfoResult = (JSONObject) jsonObject.get("movieInfoResult");

        // 영화 정보인 movieInfo 파싱
        JSONObject jsonMovieInfo = (JSONObject) jsonMovieInfoResult.get("movieInfo");
        log.info(jsonMovieInfo.toString());

        StringBuilder sb = new StringBuilder();
        // 영화 제작 국가 정보를 배열로 담은 nations 파싱
        JSONArray jsonNations = (JSONArray) jsonMovieInfo.get("nations");
        for (int i = 0; i < jsonNations.size(); i++) {
            JSONObject nation = (JSONObject) jsonNations.get(i);

            sb.append((String) nation.get("nationNm"));
            if (i != jsonNations.size() - 1)
                sb.append(","); // 구분
        }
        String nationAlt = sb.toString();

        sb = new StringBuilder();
        // 영화 장르 정보를 배열로 담은 nations 파싱
        JSONArray jsonGenres = (JSONArray) jsonMovieInfo.get("genres");
        for (int i = 0; i < jsonGenres.size(); i++) {
            JSONObject genre = (JSONObject) jsonGenres.get(i);

            sb.append((String) genre.get("genreNm"));
            if (i != jsonGenres.size() - 1)
                sb.append(","); // 구분
        }
        String genreAlt = sb.toString();

        sb = new StringBuilder();
        // 영화 감독 정보를 배열로 담은 directors 파싱
        JSONArray jsonDirectors = (JSONArray) jsonMovieInfo.get("directors");
        for (Object ob : jsonDirectors) {
            JSONObject director = (JSONObject) ob;

            sb.append((String) director.get("peopleNm"));
            sb.append("|"); // 구분
        }
        String directors = sb.toString();

        MovieDto movieDto = toDto(jsonMovieInfo, directors);
        log.info(movieDto.toString());


        return movieDto;
    }

    // 해당 영화를 Movies Table에 저장
    @Transactional
    public void save(String movieNm) throws ParseException {
        List<NaverMovieDto> naverMovieDtoList = naverMoviesService.naverMovieInfo(movieNm); // naver api로 받아온 정보들
        List<MovieDto> movieDtoList = movieInfo(movieNm);   // 영화진흥위원회 api로 받아온 정보들
        StringTokenizer st;  // 장르 구분

        // 두 api의 결과값으로 같은 영화를 찾음
        for (MovieDto ob : movieDtoList) {
            for (NaverMovieDto o : naverMovieDtoList) {
                String realMovieNm = o.getTitle().substring(3, o.getTitle().length()-4);    // <b></b> 값 제거
                // 감독명, 영어 제목 또는 원제를 비교 해서 같으면 저장
                if (o.getDirector().equals(ob.getDirectors()) && (o.getSubtitle().equalsIgnoreCase(ob.getMovieNmEn()) || realMovieNm.equals(ob.getMovieNm())) && !moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                    // 두 Dto를 하나의 Entity로 변환
                    Movies entity = ob.toEntity(o);
                    log.info(entity.toString());

                    // 영화 저장
                    moviesRepository.save(entity);

                    // 장르 저장
                    st = new StringTokenizer(ob.getGenreAlt(), ",");
                    while(st.hasMoreTokens()) {
                        Genres genreEntity = genresService.saveGenre(st.nextToken());

                        if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                            movies_has_genresRepository.save(toEntity(entity, genreEntity));
                    }

                    break;
                }
            }

            if (!moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                Movies entity = moviesRepository.save(ob.toEntity());   // 포스터가 없음

                // 장르 저장
                st = new StringTokenizer(ob.getGenreAlt(), ",");
                while(st.hasMoreTokens()) {
                    Genres genreEntity = genresService.saveGenre(st.nextToken());

                    if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                        movies_has_genresRepository.save(toEntity(entity, genreEntity));
                }
            }
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

    // 박스오피스 전용
    // 주어진 영화 코드에 맞는 정보 반환
    @Transactional
    public Movies searchForBoxOffice(String movieCd) throws ParseException {
        MovieDto search = movieDetailedInfo(movieCd); // 영화진흥위원회 영화 상세정보 api 결과값

        // DB에 있으면 불러오기
        if (moviesRepository.existsByMovieCd(search.getMovieCd())) {
            Movies entity = moviesRepository.findByMovieCd(search.getMovieCd());
            log.info(entity.toString());

            return entity;
        }

        // DB에 없으면 저장하고 불러오기
        save(search.getMovieNm());
        Movies entity = moviesRepository.findByMovieCd(search.getMovieCd());
        log.info(entity.toString());

        return entity;
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

    // JSONObject -> Dto, 필요한 데이터만 담음
    private MovieDto toDto(JSONObject item, String directors, String nationAlt, String genreAlt) {
        return MovieDto.builder()
                .movieCd((String) item.get("movieCd"))
                .movieNm((String) item.get("movieNm"))
                .movieNmEn((String) item.get("movieNmEn"))
                .nationAlt(nationAlt)
                .openDt((String) item.get("openDt"))
                .genreAlt(genreAlt)
                .directors(directors)
                .build();
    }

    // Movies + Genres -> Movie_has_genres Entity
    public Movies_has_genres toEntity(Movies movies, Genres genres) {
        return Movies_has_genres.builder()
                .genreID(genres)
                .movieCd(movies)
                .build();
    }
}
