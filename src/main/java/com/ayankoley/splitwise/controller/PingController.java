package com.ayankoley.splitwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    @RequestMapping("/ping")
    public String ping() {
        return "Pong";
    }
}
