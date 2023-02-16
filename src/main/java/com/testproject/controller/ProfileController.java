package com.testproject.controller;

import com.testproject.dto.UserDto;
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
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getMyPage(@AuthenticationPrincipal User currentUser, Model model) {
        User user = userService.findUserById(currentUser.getId());
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        return "profile";
    }

    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile multipartFile,
                              @AuthenticationPrincipal User currentUser) throws IOException {
        userService.addUserAvatar(currentUser, multipartFile);
        return "redirect:/";
    }

    @PostMapping(path = "/edit")
    public String editUserBio(@AuthenticationPrincipal User currentUser, UserDto userDto) {
        userService.editUserBio(currentUser.getId(), userDto.getBio());
        return "redirect:/";
    }
}
