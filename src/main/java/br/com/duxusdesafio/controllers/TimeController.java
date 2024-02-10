package br.com.duxusdesafio.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duxus/time")
public class TimeController {
    
    @PostMapping("/helloworld")
    public String teste() {
        return "hello world";
    }

}
