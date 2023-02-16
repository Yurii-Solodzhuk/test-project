package com.testproject.service.impl;

import com.testproject.dto.UserDto;
import com.testproject.dto.UserRegistrationDto;
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
import org.springframework.security.access.AccessDeniedException;
import java.util.Collections;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
//        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        if (userRegistrationDto.getPassword().equals("admin"))
            user.setRoles(Collections.singleton(Role.ADMIN));
        else user.setRoles(Collections.singleton(Role.USER));
        user.setAvatar("defaultAvatar.jpeg");
        userRepository.save(user);
    }

    @Override
    public void editUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).get();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
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
    public void changeUserData(Long userId, UserRegistrationDto userRegistrationDto) {
            User user = userRepository.findById(userId).get();
        if (user != null) {
            if (!userRegistrationDto.getFirstName().isEmpty())
                user.setFirstName(userRegistrationDto.getFirstName());
            if (!userRegistrationDto.getLastName().isEmpty())
                user.setLastName(userRegistrationDto.getLastName());
            if (!userRegistrationDto.getEmail().isEmpty())
                user.setEmail(userRegistrationDto.getEmail());
            if (!userRegistrationDto.getPhoneNumber().isEmpty())
                user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
            if (!userRegistrationDto.getPassword().isEmpty())
                user.setPassword(userRegistrationDto.getPassword());
            userRepository.save(user);
        }
    }

    @Override
    public boolean isEmailValid(String email) {
        User userByEmail = findUserByEmail(email);
        if (userByEmail != null)
            return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
