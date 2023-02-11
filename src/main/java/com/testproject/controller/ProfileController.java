package com.testproject.controller;

import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMyPage(Model model) {
        User userByID1 = userRepository.findById(1L).get();
        model.addAttribute("user", userByID1);
        return "profile";
    }
}
