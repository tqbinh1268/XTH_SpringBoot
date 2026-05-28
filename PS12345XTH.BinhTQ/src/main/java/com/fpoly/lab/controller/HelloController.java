package com.fpoly.lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Xin chào sinh viên FPT Polytechnic! Chúc các em học tốt Spring Boot.";
    }

    @GetMapping("/math/sum")
    public String tinhTong(@RequestParam(value = "a", defaultValue = "0") int a,
                           @RequestParam(value = "b", defaultValue = "0") int b) {
        int tong = a + b;
        return "Kết quả của " + a + " + " + b + " = " + tong;
    }

    @GetMapping("/time")
    public String showTime() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng theo kiểu dễ đọc: Ngày/Tháng/Năm Giờ:Phút:Giây
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return now.format(formatter);
    }
}
