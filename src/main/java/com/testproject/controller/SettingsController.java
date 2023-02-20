package com.testproject.controller;

import com.testproject.annotation.CurrentUserId;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class SettingsController {

    @Autowired
    private UserService userService;

    @GetMapping("/settings")
    public String getSettingsPage(@CurrentUserId Long userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        return "settings";
    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile multipartFile,
                               @CurrentUserId Long userId) throws IOException {
        userService.addUserAvatar(userId, multipartFile);
        return "redirect:/settings";
    }

    @PostMapping("/save")
    public String changeUserData(@AuthenticationPrincipal User currentUser, UserRegistrationDto userRegistrationDto,
                                 Model model) {
        User userByEmail = userService.findUserByEmail(userRegistrationDto.getEmail());
        boolean isEmailAlreadyExists = userByEmail != null;
        if (!userRegistrationDto.getEmail().isEmpty() && !userService.isEmailValid(userRegistrationDto.getEmail()) || isEmailAlreadyExists){
            model.addAttribute("user", UserMapper.INSTANCE.toDto(currentUser));
            model.addAttribute("errorMessage", "Email is not valid or already exists!");
            return "settings";
        }
        if (userRegistrationDto.getPassword().length() < 5) {
            model.addAttribute("user", UserMapper.INSTANCE.toDto(currentUser));
            model.addAttribute("errorMessage", "Password must be at least 5 characters!");
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
