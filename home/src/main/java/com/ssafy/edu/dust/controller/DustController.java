package com.ssafy.edu.dust.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dust.model.DustDto;
import com.ssafy.edu.dust.model.service.DustService;

@RestController
@RequestMapping("/dustinfo")
public class DustController {

    @Autowired
    private DustService dustService;

    @GetMapping("/dustList")
    public ResponseEntity<List<DustDto>> dustList() {
        try {
            List<DustDto> dustList = dustService.getDustInfoList();
            return new ResponseEntity<>(dustList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
