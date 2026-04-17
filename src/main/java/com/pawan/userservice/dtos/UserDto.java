package com.pawan.userservice.dtos;

import com.pawan.userservice.models.Role;
import com.pawan.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;

    public User toUser(){
        User user = new User();
        user.setId(this.getId());
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        user.setRoles(this.getRoles());
        return user;
    }

}
