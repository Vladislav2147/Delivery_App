package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.UserMapper;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/grant/{userId}")
    public void confirmUser(@PathVariable("userId") Long userId, @Param("role") String role) {
        userService.grantUserRole(userId, role);
    }
    @GetMapping("/revoke/{userId}")
    public void revokeUser(@PathVariable("userId") Long userId, @Param("role") String role) {
        userService.revokeUserRole(userId, role);
    }
}
