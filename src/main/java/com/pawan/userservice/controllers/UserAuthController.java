package com.pawan.userservice.controllers;

import com.pawan.userservice.dtos.LoginRequestDto;
import com.pawan.userservice.dtos.SignupRequestDto;
import com.pawan.userservice.dtos.UserDto;
import com.pawan.userservice.models.User;
import com.pawan.userservice.pojos.UserToken;
import com.pawan.userservice.services.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userauth")
public class UserAuthController {
    @Autowired
    private IUserAuthService userAuthService;

    @PostMapping("/signup")
    ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        try {
            User user = userAuthService.signup(signupRequestDto.getName(),
                    signupRequestDto.getEmail(),
                    signupRequestDto.getPassword());

            UserDto userDto = user.toUserDTO();

            return new ResponseEntity<>(userDto, HttpStatus.CREATED);

        }catch (Exception e){
            return null;
        }

    }

    @PostMapping("/login")
    ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto){
        try{
            UserToken userToken = userAuthService.login(loginRequestDto.getUserEmail(),
                    loginRequestDto.getPassward());

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.COOKIE, userToken.getToken());

            HttpHeaders httpHeaders = new HttpHeaders(headers);

            return new ResponseEntity<>(userToken.getUser().toUserDTO(),
                    httpHeaders,
                    HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
