package com.pawan.userservice.models;

import jakarta.persistence.Entity;

@Entity
public class Role extends BaseModel{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
