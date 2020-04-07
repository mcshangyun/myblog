package com.mcsy.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 15199
 */
@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
