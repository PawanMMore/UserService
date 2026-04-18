package com.pawan.userservice.services;

import com.pawan.userservice.models.User;
import com.pawan.userservice.pojos.UserToken;

public interface IUserAuthService {
    User signup(String name, String email, String password);
    UserToken login(String email, String password);

    Boolean validateToken(String token);
}
