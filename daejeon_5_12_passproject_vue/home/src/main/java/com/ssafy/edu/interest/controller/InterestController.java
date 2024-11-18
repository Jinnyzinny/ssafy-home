package com.ssafy.edu.interest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.edu.interest.model.InterestDto;
import com.ssafy.edu.interest.model.service.InterestService;
import com.ssafy.edu.interest.model.repository.RegionMapper;

@RestController
@RequestMapping("/interest")
public class InterestController {

    @Autowired
    private InterestService interestService;

    @Autowired
    private RegionMapper regionmapper;

    @PostMapping("/addFavoriteArea")
    public ResponseEntity<InterestDto> addFavoriteArea(
            @RequestParam("province") String provinceCode,
            @RequestParam("city") String cityCode,
            @RequestParam("dong") String dongCode) {

        try {
            String province = regionmapper.getProvinceName(provinceCode);
            String city = regionmapper.getCityName(cityCode);
            String dong = regionmapper.getDongName(dongCode);

            String lastInterestId = interestService.getLastInterestId();
            String interestId = String.valueOf(Integer.parseInt(lastInterestId) + 1);

            InterestDto interest = new InterestDto(interestId, province, city, dong);
            interestService.addInterest(interest);

            return new ResponseEntity<>(interest, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listFavoriteAreas")
    public ResponseEntity<List<InterestDto>> listFavoriteAreas() {
        try {
            List<InterestDto> interestList = interestService.listInterest();
            return new ResponseEntity<>(interestList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteFavoriteArea")
    public ResponseEntity<Void> deleteFavoriteArea(@RequestParam("interestId") String interestId) {
        try {
            interestService.deleteInterest(interestId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
