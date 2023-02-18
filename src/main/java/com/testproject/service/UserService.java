package com.testproject.service;

import com.testproject.dto.UserDto;
import com.testproject.dto.UserRegistrationDto;
import com.testproject.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    User findUserById(Long userId);
    User findUserByEmail(String email);
    List<User> getAllUsers();
    void registerUser(UserRegistrationDto userRegistrationDto);
    void editUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    void save(User user);
    Page<User> findAll(Integer page);
    void editUserBio(Long userId, String userBio);
    void addUserAvatar(User user, MultipartFile multipartFile) throws IOException;
    void changeUserData(Long id, UserRegistrationDto userRegistrationDto);
    boolean isEmailValid(String email);
    String validateInputData(UserRegistrationDto userRegistrationDto);
}
