package com.testproject.controller;

import com.testproject.model.Role;
import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping
    public String saveUser(@RequestParam("userId") User user,
                           @RequestParam String email,
                           @RequestParam String name) {

        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);
        return "redirect:/user";
    }
}
