package com.example.SWExhibition.api;

import com.example.SWExhibition.repository.BoxOfficeRepository;
import com.example.SWExhibition.service.BoxOfficeService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoxOfficeApiController {

    private final BoxOfficeService boxOfficeService;
    private final BoxOfficeRepository boxOfficeRepository;

    @GetMapping("/api/get/dailyBoxOffice")
    public List<?> dailyBoxOffice() throws ParseException {
        boxOfficeService.deleteAll();   // 초기화
        boxOfficeService.save();    // 전날 박스오피스 정보 삽입
        return boxOfficeService.returnBoxOffice();
    }

    @GetMapping("/api/search/dailyBoxOffice")
    public List<?> daiyBoxOffice() {
        return boxOfficeRepository.findAll();
    }
}
