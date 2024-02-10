package br.com.duxusdesafio.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duxus/time")
public class TimeController {
    
    @GetMapping("/helloworld")
    public String teste() {
        return "hello world";
    }

}
