package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.controller.mapper.UserMapper;
import com.shichko.deliveryservice.model.dto.StatsDto;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.entity.User;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("profile")
@Transactional
public class ProfileController {
    @Autowired
    private UserService service;
    @Autowired
    private UserMapper mapper;

    @GetMapping
    public UserDto getCurrentUser() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto dto = mapper.entityToDto((User)service.loadUserByUsername(details.getUsername()));
        return dto;
    }

    @GetMapping("/stats")
    public StatsDto getStats() {
        long userId = getCurrentUser().getId();
        return service.getStats(userId);
    }
}
