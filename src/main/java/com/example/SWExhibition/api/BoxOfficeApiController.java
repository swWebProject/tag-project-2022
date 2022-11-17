package com.example.SWExhibition.api;

import com.example.SWExhibition.service.BoxOfficeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoxOfficeApiController {

    private final BoxOfficeService boxOfficeService;

    // 박스오피스 데이터를 초기화 시키고 DB에 저장
    @GetMapping("/api/get/dailyBoxOffice")
    public List<?> dailyBoxOffice() throws ParseException {
        boxOfficeService.deleteAll();   // 초기화
        boxOfficeService.save();    // 전날 박스오피스 정보 삽입
        return boxOfficeService.returnBoxOffice();
    }

    // 박스오피스 데이터를 DB에서 내보내기
    @GetMapping("/api/search/dailyBoxOffice")
    public List<?> daiyBoxOffice() {
        return boxOfficeService.returnBoxOffice();
    }

    // 특정 날의 박스오피스 값으로 영화 데이터 저장
    @GetMapping("/api/boxoffice/save/movie/targetDate={targetDate}")
    public void toSaveMovies(@PathVariable String targetDate) throws ParseException {
        boxOfficeService.saveMovies(targetDate);
    }
}
