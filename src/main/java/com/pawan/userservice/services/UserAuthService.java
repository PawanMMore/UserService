package com.pawan.userservice.services;

import com.pawan.userservice.exceptions.IncorrectPasswordException;
import com.pawan.userservice.exceptions.UserAlreadyExistException;
import com.pawan.userservice.exceptions.UserNotRegisteredException;
import com.pawan.userservice.models.Role;
import com.pawan.userservice.models.State;
import com.pawan.userservice.models.User;
import com.pawan.userservice.repositories.RoleRepo;
import com.pawan.userservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService implements IUserAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public User signup(String name, String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("User ALready Exists! Try another Email Id.");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setCreatedAt(new Date());
        user.setLastUpdatedAt(new Date());
        user.setState(State.ACTIVE);

        Role role ;
        Optional<Role> optionalRole = roleRepo.findByName("DEFAULT");

        if(optionalRole.isEmpty()){
            role = new Role();
            role.setName("DEFAULT");
            role.setState(State.ACTIVE);
            role.setCreatedAt(new Date());
            role.setLastUpdatedAt(new Date());
            roleRepo.save(role);
        }else{
            role = optionalRole.get();
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return userRepo.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotRegisteredException("Please Register first!");
        }

        User user =optionalUser.get();
        if(password.equals(user.getPassword())){
            return user;
        }
        else{
            throw new IncorrectPasswordException("Incorrect Password entered!");
        }
    }
}
