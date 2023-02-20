package com.testproject.controller;

import com.testproject.dto.UserRegistrationDto;
import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute UserRegistrationDto userRegistrationDto, Model model) {
        model.addAttribute("userRegistrationDto", userRegistrationDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult,
                               Model model) {
        User userFromDB = userService.findUserByEmail(userRegistrationDto.getEmail());
        if (userFromDB != null){
            model.addAttribute("errorMessage", "User with such email already exists!");
            return "registration";
        }
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatPassword())){
            model.addAttribute("errorMessage", "Passwords are not match!");
            return "registration";
        }
        if (bindingResult.hasErrors())
            return "registration";
        userService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }
}
