package com.hhk.yebserver.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 */
@RestController
@Api(tags = "HelloWorld")
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
