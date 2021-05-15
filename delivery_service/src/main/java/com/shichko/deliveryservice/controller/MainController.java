package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.UserMapper;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.User;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserService service;

    @Value("${spring.profiles.active}")
    private String profile;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        UserDto dto = mapper.entityToDto(user);
        data.put("profile", dto);
        data.put("users", mapper.entitiesToDtos(service.allUsers()));
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index.html";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login.html";
//    }
}