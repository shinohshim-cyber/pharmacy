package com.my.phamacy.controller;

import com.my.phamacy.dto.DocumentDto;
import com.my.phamacy.dto.KakaoApiResponseDto;
import com.my.phamacy.service.KakaoAddressSearchService;
import com.my.phamacy.service.KakaoCategorySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FormController {
    private final KakaoAddressSearchService kakaoAddressSearchService;

    private final KakaoCategorySearchService kakaoCategorySearchService;

    @GetMapping
    public String mainForm(){
        return "main";
    }

    @GetMapping("/output")
    public String outputForm(){
        return "output";
    }

    @PostMapping("/search")
    public String searchAddress(@RequestParam("address") String address){
        //  1. 입력받은 주소로 위도, 경도 값 얻어오기
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        log.info("result : " + kakaoApiResponseDto);
        //  결과 중 Documents만 빼서 dto에 저장
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);
        log.info("도큐먼트만 출력 : " + documentDto);

        //  2. 카카오 카테고리 서비스로 반경 1km 이하 약국 정보 얻어오기
        KakaoApiResponseDto kakaoApiCategory = kakaoCategorySearchService.resultCategorySearch(documentDto.getLatitude(), documentDto.getLongitude());
        log.info("카테고리 검색 결과 : " + kakaoApiCategory);

        return "output";
    }
}
