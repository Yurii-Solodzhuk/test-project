package com.testproject.controller;

import com.testproject.annotation.CurrentUserId;
import com.testproject.dto.UserDto;
import com.testproject.mapper.UserMapper;
import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getMyPage(@CurrentUserId Long userId, Model model) {
        User user = userService.findUserById(userId);
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        return "profile";
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile multipartFile,
                              @CurrentUserId Long userId) throws IOException {
        userService.addUserAvatar(userId, multipartFile);
        return "redirect:/";
    }

    @PostMapping(path = "/edit")
    public String editUserBio(@CurrentUserId Long userId, UserDto userDto) {
        userService.editUserBio(userId, userDto.getBio());
        return "redirect:/";
    }
}
