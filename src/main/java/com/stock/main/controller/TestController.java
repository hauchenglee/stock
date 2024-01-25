package com.stock.main.controller;

import com.stock.main.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class TestController {

    @Autowired
    private StockService stockService;

    @GetMapping(value = "/test")
    public String test1() throws Exception {
        System.out.println("hello world first");
        stockService.test();
        System.out.println("hello world end");
        return "hello world";
    }
}
