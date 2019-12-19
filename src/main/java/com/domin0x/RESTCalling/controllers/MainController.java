package com.domin0x.RESTCalling.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/index", "/", ""})
    public String showMainPage(Model model) {
        return "index";
    }

}
