package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.BoxOfficeDto;
import com.example.SWExhibition.entity.BoxOffice;
import com.example.SWExhibition.entity.Movies;
import com.example.SWExhibition.repository.BoxOfficeRepository;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoxOfficeService {

    private final MoviesService moviesService;
    private final MoviesRepository moviesRepository;
    private final BoxOfficeRepository boxOfficeRepository;
    private final RestTemplate restTemplate;    // rest 방식 api 호출

    private final String boxOffieUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?"; // 영화 진흥 위원회 일별 박스오피스 api url
    private final String apiKey = "key=e6ce283cc89c58ec3419fe19ade59b0e";   // api 키 값

    private String targetDt = yesterday();   // 어제 날짜


    // 어제 날짜 구하기 yyyymmdd
    public String yesterday() {
        SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -1);

        return Format.format(cal.getTime());
    }

    // 영화 정보 불러오기
    public List<BoxOfficeDto> dailyBoxOfficeInfo() throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = boxOffieUrl +
                apiKey +
                "&targetDt=" +
                this.targetDt;

        // 영화 목록 api를 결과를 String 형태로 받음
        String data = restTemplate.getForObject(url, String.class);
        log.info(data);

        // 응답 값 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        // 가장 큰 객체인 boxOfficeResult 파싱
        JSONObject jsonBoxOfficeResult = (JSONObject) jsonObject.get("boxOfficeResult");

        // 일별 박스오피스 정보를 배열로 담은 dailyBoxOfficeList 파싱
        JSONArray jsonDailyBoxOfficeList = (JSONArray) jsonBoxOfficeResult.get("dailyBoxOfficeList");
        log.info(jsonDailyBoxOfficeList.toString());

        List<BoxOfficeDto> boxOfficeDtoList = new ArrayList<>();  // 필요한 데이터만 담은 Dto List

        // JSON 객체를 Dto로 변환하고 List에 추가
        for (Object o : jsonDailyBoxOfficeList) {
            JSONObject item = (JSONObject) o;
            boxOfficeDtoList.add(toDto(item));
            log.info(toDto(item).toString());
        }

        return boxOfficeDtoList;
    }

    // 박스 오피스 DB에 저장
    @Transactional
    public void save() throws ParseException {
        List<BoxOfficeDto> boxOfficeDtoList = dailyBoxOfficeInfo();

        // DB에 없으면 저장
        for (BoxOfficeDto dto : boxOfficeDtoList) {
            // DB에 영화가 저장되어 있지 않으면 영화와 박스오피스 둘 다 저장
            if (!moviesRepository.existsByMovieCd(dto.getMovieCd())) {
                Movies movies = moviesService.findWithMovieCode(dto.getMovieCd());
                log.info(movies.toString());

                BoxOffice entity = toEntity(dto);
                log.info(entity.toString());

                // 저장
                boxOfficeRepository.save(entity);
            }
            // 박스 오피스만 저장
            else {
                BoxOffice entity = toEntity(dto);
                log.info(entity.toString());

                // 저장
                boxOfficeRepository.save(entity);
            }
        }
    }

    // DB에 저장되어 있는 일별 박스오피스 값 주기
    @Transactional(readOnly = true)
    public List<BoxOffice> returnBoxOffice() {
        return boxOfficeRepository.findAll();
    }

    // DB에 값 전부 삭제 하루마다 리셋
    public void deleteAll() {
        boxOfficeRepository.deleteAll();
    }

    // JSONObject -> Dto
    private BoxOfficeDto toDto(JSONObject item) {
        return BoxOfficeDto.builder()
                .audiAcc((String) item.get("audiAcc"))
                .audiCnt((String) item.get("audiCnt"))
                .movieCd((String) item.get("movieCd"))
                .rank((String) item.get("rank"))
                .build();
    }

    // Dto -> Entity
    public BoxOffice toEntity(BoxOfficeDto dto) {
        return BoxOffice.builder()
                .audiAcc(dto.getAudiAcc())
                .rank(dto.getRank())
                .audiCnt(dto.getAudiCnt())
                .id(dto.getId())
                .movies(moviesRepository.findByMovieCd(dto.getMovieCd()))
                .build();
    }
}
