package com.ssafy.edu.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class HomeController1 {

	@GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/index.html");
    }
}