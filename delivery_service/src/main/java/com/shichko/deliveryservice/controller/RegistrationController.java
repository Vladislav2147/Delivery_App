package com.shichko.deliveryservice.controller;

import com.shichko.deliveryservice.exception.DeliveryServiceException;
import com.shichko.deliveryservice.model.dto.UserDto;
import com.shichko.deliveryservice.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public void addUser(@RequestBody @Valid UserDto userDto, HttpServletResponse response) throws IOException {
        try {
            userService.saveUser(userDto);
            response.setStatus(HttpStatus.CREATED.value());
        } catch (DeliveryServiceException e) {
            response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
        }
    }
}