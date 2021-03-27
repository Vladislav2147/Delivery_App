package com.shichko.delivery_service.controller;

import com.shichko.delivery_service.controller.mapper.UserMapper;
import com.shichko.delivery_service.model.dto.UserDto;
import com.shichko.delivery_service.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper mapper;

    @GetMapping
    public List<UserDto> userList() {
        return mapper.entitiesToDtos(userService.allUsers());
    }

    @PostMapping
    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    @GetMapping("/grant/{userId}")
    public String confirmUser(@PathVariable("userId") Long userId) {
        userService.confirmUser(userId);
        return "admin";
    }
}
