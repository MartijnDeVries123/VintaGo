package com.example.vintagoapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {

    @GetMapping("/test")
    public String main() {
        return "Hello World";
    }

    @GetMapping("/nee")
    public String test2() {
        return "hoop van zegen";
    }
}
