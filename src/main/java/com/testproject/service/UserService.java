package com.testproject.service;

import com.testproject.dto.UserDto;
import com.testproject.dto.UserRegistrationDto;
import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Service
public interface UserService {
    User findUserById(Long userId);
    User findUserByEmail(String email);
    void registerUser(UserRegistrationDto userRegistrationDto);
    void editUser(Long userId, UserDto userDto);
    void deleteUser(Long userId);
    Page<User> findAll(Integer page);
    void editUserBio(Long userId, String userBio);
    void addUserAvatar(User user, MultipartFile multipartFile) throws IOException;
    void changeUserData(Long id, UserRegistrationDto userRegistrationDto);
    boolean isEmailValid(String email);

}
