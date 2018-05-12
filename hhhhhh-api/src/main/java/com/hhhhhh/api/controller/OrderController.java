package com.hhhhhh.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @GetMapping("/index")
    public String index() {
        return "{\"id\":1}";
    }
}
