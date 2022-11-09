package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.NaverMovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverMoviesService {

    private final RestTemplate restTemplate;    // rest 방식 api 호출

    // 네이버 영화 API header 값
    private final String NaverClientId = "3utscYqdprdRrHBTetdx";   // X-Naver-Client-Id, 네이버 api Id
    private final String NaverClientSecret = "aPmyWwnGqR";   // X-Naver-Client-Secret, 네이버 api Id

    private final String NaverMovieUrl = "https://openapi.naver.com/v1/search/movie.json?query="; // 네이버 영화 api url

    // 영화 정보 불러오기
    public List<NaverMovieDto> naverMovieInfo(String movieNm) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String apiUrl = new StringBuilder(NaverMovieUrl)
                .append(movieNm)
                .toString();

        // 해더 값 지정
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", NaverClientId);
        headers.set("X-Naver-Client-Secret", NaverClientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 네이버 api로 받은 정보에서 Body 값만 받음
        String data = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class).getBody();
        log.info(data);

        // 응답 값 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        // 영화 정보를 배열로 담은 items 파싱
        JSONArray jsonItems = (JSONArray) jsonObject.get("items");
        log.info(jsonItems.toString());

        List<NaverMovieDto> naverMovieDtoList = new ArrayList<>();  // 필요한 데이터만 담은 Dto List

        // JSON 객체를 Dto로 변환하고 List에 추가
        for (Object o : jsonItems) {
            JSONObject item = (JSONObject) o;
            naverMovieDtoList.add(toDto(item));
            log.info(toDto(item).toString());
        }

        return naverMovieDtoList;
    }

    // JSONObject -> Dto, 필요한 데이터만 담음
    private NaverMovieDto toDto(JSONObject item) {

        return NaverMovieDto.builder()
                .title((String) item.get("title"))
                .subtitle((String) item.get("subtitle"))
                .image((String) item.get("image"))
                .director((String) item.get("director"))
                .actor((String) item.get("actor"))
                .build();
    }
}
