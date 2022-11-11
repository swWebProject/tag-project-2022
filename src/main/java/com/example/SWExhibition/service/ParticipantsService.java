package com.example.SWExhibition.service;

import com.example.SWExhibition.dto.FilmoInfo;
import com.example.SWExhibition.dto.ParticipantsDto;
import com.example.SWExhibition.entity.Participants;
import com.example.SWExhibition.repository.ParticipantsRepository;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipantsService {

    private final ParticipantsRepository participantsRepository;
    private final Movies_has_participantsService movies_has_participantsService;
    private final RestTemplate restTemplate;

    private final String participantsUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?"; // 영화인 목록 api url
    private final String participantsDetailedUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json?"; // 영화인 상세정보 api url
    private final String apiKey = "key=4801347d5095e67e89bbba73e7650760";   // api 키 값

    // 영화인 목록
    // 영화인 정보 불러오기
    public List<ParticipantsDto> peopleInfo(String peopleNm) throws ParseException {
        // 받아온 영화 이름을 url에 넣음
        String url = new StringBuilder(participantsUrl)
                .append(apiKey)
                .append("&peopleNm=")
                .append(peopleNm)
                .toString();

        // 영화인 목록 api를 결과를 String 형태로 받음
        String data = restTemplate.getForObject(url, String.class);
        log.info(data);

        // 응답 값 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(data);

        // 가장 큰 객체인 peopleListResult 파싱
        JSONObject jsonPeopleListResult = (JSONObject) jsonObject.get("peopleListResult");

        // 영화인 정보를 배열로 담은 peopleList 파싱
        JSONArray jsonPeopleList = (JSONArray) jsonPeopleListResult.get("peopleList");
        log.info(jsonPeopleList.toString());

        List<ParticipantsDto> participantsDtoList = new ArrayList<>();  // 필요한 데이터만 담은 Dto List

        // JSON 객체를 Dto로 변환하고 List에 추가
        for (Object o : jsonPeopleList) {
            JSONObject item = (JSONObject) o;

            // 감독 또는 배우만 가져오기
            if ((item.get("repRoleNm")).equals("배우") || (item.get("repRoleNm")).equals("감독"))
                participantsDtoList.add(toDto(item));
        }

        log.info(participantsDtoList.toString());

        return participantsDtoList;
    }

    // 영화인 상세정보
    // 영화인 상세정보 불러오기
    public List<ParticipantsDto> peopleDetailedInfo(String peopleNm) throws ParseException {
        List<ParticipantsDto> participantsDtoList = peopleInfo(peopleNm);

        // 영화인 목록에서 가져온 사람의 상세 정보를 덧붙임
        for (ParticipantsDto dto : participantsDtoList) {
            // 받아온 영화 이름을 url에 넣음
            String url = participantsDetailedUrl +
                    apiKey +
                    "&peopleCd=" +
                    dto.getPeopleCd();

            // 영화인 목록 api를 결과를 String 형태로 받음
            String data = restTemplate.getForObject(url, String.class);
            log.info(data);

            // 응답 값 파싱
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(data);


            // 가장 큰 객체인 peopleListResult 파싱
            JSONObject jsonPeopleInfoResult = (JSONObject) jsonObject.get("peopleInfoResult");


            // 영화인 정보를 담는 peopleInfo 파싱
            JSONObject jsonPeopleInfo = (JSONObject) jsonPeopleInfoResult.get("peopleInfo");
            log.info(jsonPeopleInfo.toString());

            // 영화인의 성별을 담는 sex 파싱
            Object objectSex = jsonPeopleInfo.get("sex");
            if (objectSex != null)
                dto.setSex(objectSex.toString());    // 파싱한 값 받아오기

            // 필모그래피 배열을 갖는 filmos 파싱
            JSONArray jsonFilmos = (JSONArray) jsonPeopleInfo.get("filmos");
            log.info(jsonFilmos.toString());

            List<FilmoInfo> filmoInfoList = new ArrayList<>();  // 필모그래피 정보 리스트

            // 참여한 작품 조회
            for (Object o : jsonFilmos) {
                JSONObject item = (JSONObject) o;

                // dto에 있는 필모그래피 정보 리스트에 해당 값들을 FilmoInfo 객체로 만들어 추가
                filmoInfoList.add(makeFilmoInfo((String) item.get("movieCd"), (String) item.get("moviePartNm")));
            }

            dto.setFilmoInfoList(filmoInfoList);
        }

        log.info(participantsDtoList.toString());

        return participantsDtoList;
    }

    // 영화인 정보 저장
    @Transactional
    public void save(String peopleNm) throws ParseException {
        List<ParticipantsDto> participantsDtoList = peopleDetailedInfo(peopleNm);   // 완성된 영화인 정보 불러오기
        Participants entity;

        // DB에 존재 여부로 저장하기
        for (ParticipantsDto dto : participantsDtoList) {
            entity = dto.toEntity(); // Dto -> Entity
            log.info(entity.toString());
            // Db에 없으면 해당 영화인 저장
            if (!participantsRepository.existsByPeopleCd(dto.getPeopleCd())) {
                participantsRepository.save(entity);    // 영화인 저장
                log.info(dto.toString());
            }

            movies_has_participantsService.save(dto);   // 영화 & 영화인 저장
        }
    }


    // JSONObject -> Dto, 필요한 데이터만 담음
    private ParticipantsDto toDto(JSONObject item) {
        return ParticipantsDto.builder()
                .peopleCd((String) item.get("peopleCd"))
                .peopleNmEn((String) item.get("peopleNmEn"))
                .repRoleNm((String) item.get("repRoleNm"))
                .peopleNm((String) item.get("peopleNm"))
                .build();
    }

    // FilmoInfo 객체 생성
    public FilmoInfo makeFilmoInfo(String movieCd, String moviePartNm) {
        return FilmoInfo.builder()
                .movieCd(movieCd)
                .moviePartNm(moviePartNm)
                .build();
    }
}
























