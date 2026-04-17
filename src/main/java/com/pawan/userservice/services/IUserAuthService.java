package com.pawan.userservice.services;

import com.pawan.userservice.models.User;

public interface IUserAuthService {
    User signup(String name, String email, String password);
    User login(String email,String password);
}
