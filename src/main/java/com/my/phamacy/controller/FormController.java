package com.my.phamacy.controller;

import com.my.phamacy.dto.DocumentDto;
import com.my.phamacy.dto.KakaoApiResponseDto;
import com.my.phamacy.service.KakaoAddressSearchService;
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
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        log.info("result : " + kakaoApiResponseDto);
        //  결과 중 Documents만 빼서 dto에 저장
        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);
        log.info("DocumentDto : " + documentDto);
        

        return "output";
    }
}
