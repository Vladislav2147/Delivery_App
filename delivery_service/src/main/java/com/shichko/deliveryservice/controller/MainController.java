package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index.html";
    }

}