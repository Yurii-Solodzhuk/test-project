package com.testproject.service;

import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {
    User findUserById(Long userId);
    User findUserByEmail(String email);
    void registerUser(User user);
    void editUser(User user, String firstName, String lastName, String phoneNumber, String email);
    void deleteUser(Long userId);
    Page<User> findAll(Integer page);
    void editUserBio(Long userId, String userBio);
    void addUserAvatar(User user, MultipartFile multipartFile) throws IOException;

    void changeUserData(Long id, User userFromForm);

}
