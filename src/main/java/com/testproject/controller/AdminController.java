package com.testproject.controller;

import com.testproject.dto.UserDto;
import com.testproject.dto.UserRegistrationDto;
import com.testproject.dto.UserResponseDto;
import com.testproject.model.User;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        if (userId == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User user = userService.findUserById(userId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        if (!userService.validateInputData(userRegistrationDto).isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userService.validateInputData(userRegistrationDto));
        if (!userService.isEmailValid(userRegistrationDto.getEmail()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is not valid");
        if (userService.findUserByEmail(userRegistrationDto.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        if (!userRegistrationDto.getPassword().isEmpty() && !userRegistrationDto.getPassword().equals(userRegistrationDto.getRepeatPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords are not match");
        userService.registerUser(userRegistrationDto);
        return new ResponseEntity<>(userRegistrationDto, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        if (userDto == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (userService.findUserByEmail(userDto.getEmail()) != null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        userService.editUser(userId, userDto);
        UserResponseDto userResponseDto = new UserResponseDto(userService.findUserById(userId));
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
