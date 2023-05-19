package com.nozuch.securitydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasAuthority('sys:user:test')")
    public String hello2() {
        return "hello2";
    }
}
