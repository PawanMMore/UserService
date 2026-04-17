package com.pawan.userservice.services;

import com.pawan.userservice.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements IUserAuthService {

    @Override
    public User signup(String name, String email, String password) {
        return null;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }
}
