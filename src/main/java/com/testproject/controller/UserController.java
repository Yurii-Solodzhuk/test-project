package com.testproject.controller;

import com.testproject.model.Role;
import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.IntStream;

@Controller
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUserList(@AuthenticationPrincipal User currentUser,
                              Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        Page<User> pageUsers = userRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("usersPage", pageUsers);
        model.addAttribute("numbers", IntStream.range(0, pageUsers.getTotalPages()).toArray());
        boolean isAdmin = currentUser.getRoles().contains(Role.ADMIN);
        model.addAttribute("isAdmin", isAdmin);
        return "user-list";
    }

    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping
    public String saveUser(@RequestParam("userId") User user,
                           @RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String phoneNumber,
                           @RequestParam String email) {

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long userId){
        userRepository.deleteById(userId);
        return "redirect:/user";
    }
}
