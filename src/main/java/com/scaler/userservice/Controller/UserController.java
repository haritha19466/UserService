package com.scaler.userservice.Controller;

import java.util.Optional;
import com.scaler.userservice.Dtos.*;
import com.scaler.userservice.Models.Token;
import com.scaler.userservice.Models.User;
import com.scaler.userservice.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }
    @PostMapping("/signup")
    public UserDto signUp(@RequestBody signupRequestdto signupdto){
            User user=userService.signup(signupdto.getEmail(),signupdto.getPassword(),signupdto.getName());
            return UserDto.from(user);
    }
    @PostMapping("/login")
    public logindto loginuser(@RequestBody LoginRequestdto loginRequestdto){
        Token token = userService.loginuser(loginRequestdto.getEmail(), loginRequestdto.getPassword());
        return logindto.from(token);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Logoutdto logoutdto){
        userService.logout(logoutdto.getTokenvalue());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable String token){
        User user=userService.validateToken(token);
        if(user!=null) {
            return UserDto.from(user);
        }
        return null;
    }
}
