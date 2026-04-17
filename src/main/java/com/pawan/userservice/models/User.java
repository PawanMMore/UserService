package com.pawan.userservice.models;

import com.pawan.userservice.dtos.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String name;
    private String email;
    private String password;

    @ManyToMany
    private List<Role> roles;

    public UserDto toUserDTO() {
        UserDto userDto = new UserDto();
        userDto.setEmail(this.email);
        userDto.setName(this.name);
        userDto.setRoles(this.roles);
        userDto.setId(this.getId());
        return userDto;
    }
}
