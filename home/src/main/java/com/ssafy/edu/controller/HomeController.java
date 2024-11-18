package com.ssafy.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")  // 클래스 수준에서 /home 경로 설정
public class HomeController {

    @GetMapping
    public String home() {
        return "index";  // index.jsp 페이지로 이동
    }
}
