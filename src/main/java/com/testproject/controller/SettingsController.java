package com.testproject.controller;

import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/settings")
    public String getSettingsPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", userRepository.findById(user.getId()).get());
        return "settings";
    }

    @PostMapping("/save")
    public String registerUser(@AuthenticationPrincipal User currentUser,
                               User userFromForm) {
        User user = userRepository.findById(currentUser.getId()).get();
        if (user != null) {
            if (!userFromForm.getFirstName().isEmpty())
                user.setFirstName(userFromForm.getFirstName());
            if (!userFromForm.getLastName().isEmpty())
                user.setLastName(userFromForm.getLastName());
            if (!userFromForm.getEmail().isEmpty())
                user.setEmail(userFromForm.getEmail());
            if (!userFromForm.getPhoneNumber().isEmpty())
                user.setPhoneNumber(userFromForm.getPhoneNumber());
            if (!userFromForm.getPassword().isEmpty())
                user.setPassword(userFromForm.getPassword());
            userRepository.save(user);
        }
        return "redirect:/settings";
    }
}
