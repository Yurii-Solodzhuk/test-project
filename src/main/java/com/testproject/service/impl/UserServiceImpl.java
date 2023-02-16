package com.testproject.service.impl;

import com.testproject.model.Role;
import com.testproject.model.User;
import com.testproject.repository.UserRepository;
import com.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void registerUser(User user) {
        //        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setAvatar("defaultAvatar.jpeg");
        userRepository.save(user);
    }

    @Override
    public void editUser(User user, String firstName, String lastName, String phoneNumber, String email) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        }
    }

    @Override
    public Page<User> findAll(Integer page) {
        return userRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public void editUserBio(Long userId, String userBio) {
        User user = userRepository.findById(userId).get();
        user.setBio(userBio);
        userRepository.save(user);
    }

    @Override
    public void addUserAvatar(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "-" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(uploadPath + "/" + resultFilename));

            user.setAvatar(resultFilename);
            userRepository.save(user);

        }
    }

    @Override
    public void changeUserData(Long userId, User userFromForm) {
        User user = userRepository.findById(userId).get();
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
    }
}
