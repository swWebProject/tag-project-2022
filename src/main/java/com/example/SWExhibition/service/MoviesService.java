package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.*;
import com.example.SWExhibition.entity.*;
import com.example.SWExhibition.repository.Movie_has_participantsRepository;
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
    private final Movies_has_genresRepository movies_has_genresRepository;
    private final ParticipantsService participantsService;
    private final Movie_has_participantsRepository movie_has_participantsRepository;
    private final RestTemplate restTemplate;    // rest 방식 api 호출

    private final String movieDetailedUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";  // 영화진흥위원회 영화 상세정보 api url
    private final String movieUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?"; // 영화진흥위원회 영화 목록 api url
    private final String apiKey = "key=e6ce283cc89c58ec3419fe19ade59b0e";   // api 키 값

    // 영화 목록
    // 영화 정보 불러오기
    public List<MovieDto> movieInfo(String movieNm) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = movieUrl +
                apiKey +
                "&itemPerPage=30" +
                "&movieNm=" +
                movieNm;

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
    public MovieDto movieDetailedInfo(String movieCd) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = movieDetailedUrl +
                apiKey +
                "&movieCd=" +
                movieCd;

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

        MovieDto movieDto = toDto(jsonMovieInfo, directors, nationAlt, genreAlt);
        log.info(movieDto.toString());


        return movieDto;
    }


    // 해당 영화를 Movies Table에 저장
    @Transactional
    public void save(String movieNm) throws ParseException {
        List<NaverMovieDto> naverMovieDtoList = naverMoviesService.naverMovieInfo(movieNm); // naver api로 받아온 정보들
        List<MovieDto> movieDtoList = movieInfo(movieNm);   // 영화진흥위원회 api로 받아온 정보들
        StringTokenizer st;  // 장르 구분, 배우명 구분

        // 두 api의 결과값으로 같은 영화를 찾음
        for (MovieDto ob : movieDtoList) {
            for (NaverMovieDto o : naverMovieDtoList) {
                String compareMovieNm = "<b>" + ob.getMovieNm() + "</b>";    // 사이에 <b></b> 값 추가
                // 감독명, 영어 제목 또는 원제를 비교 해서 같으면 저장
                if ((o.getDirector().equals(ob.getDirectors()) || convertName(o.getDirector()).equals(convertName(ob.getDirectors()))) && (o.getSubtitle().equalsIgnoreCase(ob.getMovieNmEn()) || compareMovieNm.equals(o.getTitle())) && !moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                    // 두 Dto를 하나의 Entity로 변환
                    Movies entity = ob.toEntity(o);
                    log.info(entity.toString());

                    // 영화 저장
                    moviesRepository.save(entity);

                    // 장르 저장
                    if (ob.getGenreAlt() != null) {
                        st = new StringTokenizer(ob.getGenreAlt(), ",");
                        while (st.hasMoreTokens()) {
                            Genres genreEntity = genresService.saveGenre(st.nextToken());

                            if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                                movies_has_genresRepository.save(toEntity(entity, genreEntity));
                        }
                    }

                    break;
                }
            }

            if (!moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                Movies entity = moviesRepository.save(ob.toEntity());   // 포스터가 없음

                // 장르 저장
                if (ob.getGenreAlt() != null) {
                    st = new StringTokenizer(ob.getGenreAlt(), ",");
                    while (st.hasMoreTokens()) {
                        Genres genreEntity = genresService.saveGenre(st.nextToken());

                        if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                            movies_has_genresRepository.save(toEntity(entity, genreEntity));
                    }
                }

            }
        }
    }


    // 해당 영화만 저장
    // 해당 영화를 Movies Table에 저장
    @Transactional
    public void save(MovieDto ob) throws ParseException {
        List<NaverMovieDto> naverMovieDtoList = naverMoviesService.naverMovieInfo(ob.getMovieNm()); // naver api로 받아온 정보들
        StringTokenizer st;  // 장르 구분, 배우명 구분

        // 두 api의 결과값으로 같은 영화를 찾음
        for (NaverMovieDto o : naverMovieDtoList) {
            String compareMovieNm = "<b>" + ob.getMovieNm() + "</b>";    // 사이에 <b></b> 값 추가
            // 감독명, 영어 제목 또는 원제를 비교 해서 같으면 저장
            if ((o.getDirector().equals(ob.getDirectors()) || convertName(o.getDirector()).equals(convertName(ob.getDirectors()))) && (o.getSubtitle().equalsIgnoreCase(ob.getMovieNmEn()) || compareMovieNm.equals(o.getTitle())) && !moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                // 두 Dto를 하나의 Entity로 변환
                Movies entity = ob.toEntity(o);
                log.info(entity.toString());

                // 영화 저장
                moviesRepository.save(entity);

                // 장르 저장
                if (ob.getGenreAlt() != null) {
                    st = new StringTokenizer(ob.getGenreAlt(), ",");
                    while (st.hasMoreTokens()) {
                        Genres genreEntity = genresService.saveGenre(st.nextToken());

                        if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                            movies_has_genresRepository.save(toEntity(entity, genreEntity));
                    }
                }

                break;
            }

            else if (!moviesRepository.existsByMovieCd(ob.getMovieCd())) {
                Movies entity = moviesRepository.save(ob.toEntity());   // 포스터가 없음
                log.info(entity.toString());

                // 장르 저장
                if (ob.getGenreAlt() != null) {
                    st = new StringTokenizer(ob.getGenreAlt(), ",");
                    while (st.hasMoreTokens()) {
                        Genres genreEntity = genresService.saveGenre(st.nextToken());

                        if (!movies_has_genresRepository.existsByMovieIdAndGenreID(entity, genreEntity))
                            movies_has_genresRepository.save(toEntity(entity, genreEntity));
                    }
                }

            }

        }
    }

    // 영화 이름과 감독 또는 주요 배우들로 영화 저장
    @Transactional
    public void saveAsPeople(String peopleNm, String movieNm) throws ParseException {
        List<ParticipantsDto> peopleInfoList = participantsService.peopleInfo(peopleNm, movieNm); // 영화 이름으로 감독 또는 배우의 정보 가져오기
        List<ParticipantsDto> participantsDtoList = participantsService.peopleDetailedInfo(peopleInfoList); // 위의 리스트에 세부 정보 추가
        participantsService.save(participantsDtoList);  // 배우들 정보를 DB에 저장

        for (ParticipantsDto dto : participantsDtoList) {
            Participants participant = participantsService.findPeopleCd(dto.getPeopleCd());

            List<FilmoInfo> filmoInfoList = dto.getFilmoInfoList(); // 필모그래피에 대한 정보 리스트
            for (FilmoInfo info : filmoInfoList) {
                String moviePartNm = info.getMoviePartNm(); // 참여 분야
                Movies movies = findWithMovieCode(info.getMovieCd());   // 해당 영화 코드에 맞는 정보
                log.info(movies.toString());

                // 영화, 영화인, 참여분야 세 가지를 중복하는 값이 없으면 저장
                if (!movie_has_participantsRepository.existsByMoiveIdAndPeopleIdAndMoviePartNm(movies, participant, moviePartNm)) {
                    Movies_has_participants mapping = toEntity(movies, participant, moviePartNm);  // 영화 & 영화인 entity
                    movie_has_participantsRepository.save(mapping); // 매핑한 값 저장
                    log.info(mapping.toString());
                }
            }
        }
    }

    // 감독 또는 주요 배우들로 영화 저장
    @Transactional
    public void saveAsPeople(String peopleNm) throws ParseException {
        List<ParticipantsDto> participantsDtoList = participantsService.peopleDetailedInfo(peopleNm); // 감독 또는 배우의 정보 가져오기
        participantsService.save(participantsDtoList);  // 배우들 정보를 DB에 저장

        for (ParticipantsDto dto : participantsDtoList) {
            Participants participant = participantsService.findPeopleCd(dto.getPeopleCd());

            List<FilmoInfo> filmoInfoList = dto.getFilmoInfoList(); // 필모그래피에 대한 정보 리스트
            for (FilmoInfo info : filmoInfoList) {
                log.info(info.toString());
                String moviePartNm = info.getMoviePartNm(); // 참여 분야
                Movies movies = findWithMovieCode(info.getMovieCd());   // 해당 영화 코드에 맞는 정보
                log.info(movies.toString());

                // 영화, 영화인, 참여분야 세 가지를 중복하는 값이 없으면 저장
                if (!movie_has_participantsRepository.existsByMoiveIdAndPeopleIdAndMoviePartNm(movies, participant, moviePartNm)) {
                    Movies_has_participants mapping = toEntity(movies, participant, moviePartNm);  // 영화 & 영화인 entity
                    movie_has_participantsRepository.save(mapping); // 매핑한 값 저장
                    log.info(mapping.toString());
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

    
    // 주어진 영화 코드에 맞는 정보 반환
    @Transactional
    public Movies findWithMovieCode(String movieCd) throws ParseException {
        // DB에 있으면 불러오기
        if (moviesRepository.existsByMovieCd(movieCd)) {
            Movies entity = moviesRepository.findByMovieCd(movieCd);
            log.info(entity.toString());

            return entity;
        }

        // DB에 없으면 저장하고 불러오기
        MovieDto search = movieDetailedInfo(movieCd); // 영화진흥위원회 영화 상세정보 api 결과값
        save(search);
        Movies entity = moviesRepository.findByMovieCd(search.getMovieCd());
        log.info(entity.toString());

        return entity;
    }


    // DB에서 코드에 맞는 영화 가져오기
    @Transactional(readOnly = true)
    public Movies show(String movieCd) {
        return moviesRepository.findByMovieCd(movieCd);
    }

    // 영화 포스터 Url 수정
    @Transactional
    public Movies updatePoster(PosterUrlDto dto) {
        Movies updated = moviesRepository.findByMovieCd(dto.getMovieCd());   // 업데이트 할 Entity
        updated.setPoster(dto.getPosterUrl());
        log.info(updated.toString());

        return moviesRepository.save(updated);
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
    private Movies_has_genres toEntity(Movies movies, Genres genres) {
        return Movies_has_genres.builder()
                .genreID(genres)
                .movieCd(movies)
                .build();
    }

    // Movies + Participants -> Mvoie_has_participants Entity
    private Movies_has_participants toEntity(Movies movies, Participants participants, String moviePartNm) {
        return Movies_has_participants.builder()
                .peopleId(participants)
                .moviePartNm(moviePartNm)
                .moiveId(movies)
                .build();
    }

    // 초성 검색
    private String convertName( String name ){

        StringBuilder rtName = new StringBuilder();

        char epName;

        try{
            for (int i=0; i<name.length(); i++){
                epName = name.charAt(i);
                rtName.append(Direct(epName));
            }
        }catch (Exception e) {
            log.info(e.toString());

        }



        return rtName.toString();

    }

    public String Direct(char b){

        String chosung = null;

        int first = (b - 44032 ) / ( 21 * 28 );

        switch(first){

            case 0:

                chosung="ㄱ";

                break;

            case 1:

                chosung="ㄲ";

                break;

            case 2:

                chosung="ㄴ";

                break;

            case 3:

                chosung="ㄷ";

                break;

            case 4:

                chosung="ㄸ";

                break;

            case 5:

                chosung="ㄹ";

                break;

            case 6:

                chosung="ㅁ";

                break;

            case 7:

                chosung="ㅂ";

                break;

            case 8:

                chosung="ㅃ";

                break;

            case 9:

                chosung="ㅅ";

                break;

            case 10:

                chosung="ㅆ";

                break;

            case 11:

                chosung="ㅇ";

                break;

            case 12:

                chosung="ㅈ";

                break;

            case 13:

                chosung="ㅉ";

                break;

            case 14:

                chosung="ㅊ";

                break;

            case 15:

                chosung="ㅋ";

                break;

            case 16:

                chosung="ㅌ";

                break;

            case 17:

                chosung="ㅍ";

                break;

            case 18:

                chosung="ㅎ";

                break;

            default: chosung=String.valueOf(b);

        }

        return chosung;

    }

    // 영화 이름으로 검색
    @Transactional(readOnly = true)
    public List<Movies> searchMovies(String keyword) {
        List<Movies> movies = moviesRepository.findByMovieNmContains(keyword);
        List<Movies> movieDtoList = new ArrayList<>();

        if (movies.isEmpty()) return null;

        for(Movies movie : movies) {
            movieDtoList.add(movie);
        }
        return movieDtoList;
    }
}
