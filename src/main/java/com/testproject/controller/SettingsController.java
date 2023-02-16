package com.testproject.controller;

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
    public String getSettingsPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userService.findUserById(user.getId()));
        return "settings";
    }

    @PostMapping("/save")
    public String changeUserData(@AuthenticationPrincipal User currentUser, User userFromForm) {
        userService.changeUserData(currentUser.getId(), userFromForm);
        return "redirect:/settings";
    }
}
