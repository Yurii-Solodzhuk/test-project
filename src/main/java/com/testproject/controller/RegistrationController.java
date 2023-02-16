package com.testproject.controller;

import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        User userFromDB = userService.findUserByEmail(user.getEmail());
        if (userFromDB != null){
            model.addAttribute("wanrMessage", "User already exists!");
            return "registration";
        }
        userService.registerUser(user);
        return "redirect:/login";
    }
}
