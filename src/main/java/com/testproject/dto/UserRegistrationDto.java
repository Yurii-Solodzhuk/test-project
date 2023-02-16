package com.testproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserRegistrationDto {
    @NotEmpty(message = "Enter your first name")
    private String firstName;

    @NotBlank(message = "Enter your last name")
    private String lastName;

    @NotBlank(message = "Enter your phone number")
    private String phoneNumber;

    @NotBlank(message = "Enter your email")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotBlank(message = "Enter your password")
//    @Length(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Repeat your password")
    private String repeatPassword;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String firstName, String lastName, String phoneNumber, String email, String password, String repeatPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
