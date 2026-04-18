package com.pawan.userservice.services;

import com.pawan.userservice.exceptions.IncorrectPasswordException;
import com.pawan.userservice.exceptions.UserAlreadyExistException;
import com.pawan.userservice.exceptions.UserNotRegisteredException;
import com.pawan.userservice.models.Role;
import com.pawan.userservice.models.Session;
import com.pawan.userservice.models.State;
import com.pawan.userservice.models.User;
import com.pawan.userservice.pojos.UserToken;
import com.pawan.userservice.repositories.RoleRepo;
import com.pawan.userservice.repositories.SessionRepo;
import com.pawan.userservice.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserAuthService implements IUserAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signup(String name, String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistException("User ALready Exists! Try another Email Id.");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
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
    public UserToken login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotRegisteredException("Please Register first!");
        }

        User user =optionalUser.get();
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            Map<String, Object> payload = new HashMap<>();
            Long nowinMillis = System.currentTimeMillis();
            payload.put("iat", nowinMillis);
            payload.put("exp", nowinMillis+10000);
            payload.put("userId", user.getId());
            payload.put("iss", "Param");
            payload.put("scope", user.getRoles());

            MacAlgorithm algorithm = Jwts.SIG.HS256;
            SecretKey secretKey = algorithm.key().build();

            String token = Jwts.builder().claims(payload).signWith(secretKey).compact();
            System.out.println(token.length());
            System.out.println(token);

            Session session = new Session();
            session.setUser(user);
            session.setToken(token);
            session.setState(State.ACTIVE);
            sessionRepo.save(session);

            return new UserToken(user, token);
        }
        else{
            throw new IncorrectPasswordException("Incorrect Password entered!");
        }
    }
}
