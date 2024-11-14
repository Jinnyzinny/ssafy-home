package com.ssafy.edu.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptExample {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "1";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);
    }
}