package com.testproject.controller;

import com.testproject.dto.UserDto;
import com.testproject.mapper.UserMapper;
import com.testproject.model.Role;
import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private UserService userService;

    @GetMapping
    public String getUserList(@AuthenticationPrincipal User currentUser,
                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                              Model model) {
        Page<User> pageUsers = userService.findAll(page);
        model.addAttribute("usersPage", pageUsers);
        model.addAttribute("numbers", IntStream.range(0, pageUsers.getTotalPages()).toArray());

        model.addAttribute("isAdmin", currentUser.getRoles().contains(Role.ADMIN));
        return "user-list";
    }

    @GetMapping("{user}")
    public String getUserEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", UserMapper.INSTANCE.toDto(user));
        return "user-edit";
    }

    @PostMapping("{userId}")
    public String editUser(@PathVariable Long userId, @ModelAttribute UserDto userDto, Model model) {
        if (!userService.isEmailValid(userDto.getEmail())){
            model.addAttribute("user", UserMapper.INSTANCE.toDto(userService.findUserById(userId)));
            model.addAttribute("errorMessage", "Email is not valid or already exists!");
            return "user-edit";
        }
        userService.editUser(userId, userDto);
        return "redirect:/user";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long userId){
        userService.deleteUser(userId);
        return "redirect:/user";
    }
}
