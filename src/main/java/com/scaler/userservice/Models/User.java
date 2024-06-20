package com.scaler.userservice.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String hashedpassword;
    @ManyToMany
    private List<Roles> roles;
    private boolean is_verified;
}
