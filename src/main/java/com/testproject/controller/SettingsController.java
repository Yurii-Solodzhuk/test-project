package com.testproject.controller;

import com.testproject.dto.UserRegistrationDto;
import com.testproject.mapper.UserMapper;
import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    @Autowired
    private UserService userService;

    @GetMapping("/settings")
    public String getSettingsPage(@AuthenticationPrincipal User currentUser, Model model) {
        User user = userService.findUserById(currentUser.getId());
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        return "settings";
    }

    @PostMapping("/save")
    public String changeUserData(@AuthenticationPrincipal User currentUser, UserRegistrationDto userRegistrationDto,
                                 Model model) {
        if (!userService.isEmailValid(userRegistrationDto.getEmail())){
            model.addAttribute("user", UserMapper.INSTANCE.toDto(currentUser));
            model.addAttribute("errorMessage", "Email is not valid or already exists!");
            return "settings";
        }
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatPassword())){
            model.addAttribute("user", UserMapper.INSTANCE.toDto(currentUser));
            model.addAttribute("errorMessage", "Passwords are not match!");
            return "settings";
        }
        userService.changeUserData(currentUser.getId(), userRegistrationDto);
        return "redirect:/settings";
    }
}
