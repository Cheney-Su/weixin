package com.doubles.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test")
    public String test() {
        System.out.println("test");
        return "test";
    }
}
