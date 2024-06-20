package com.scaler.userservice.Services;

import com.scaler.userservice.Models.Token;
import com.scaler.userservice.Models.User;
import com.scaler.userservice.Repositories.Tokenrepo;
import com.scaler.userservice.Repositories.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.boot.registry.selector.StrategyRegistration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class UserService {
    private UserRepo userRepo;
    private Tokenrepo tokenrepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,UserRepo userRepo,Tokenrepo tokenrepo){
        // here we are getting red line for variable of bcrypt encoder since it is not registred
        // as bean but we cannot make it as bean in its own implemenattion which is a third party api
        // so wil configure this as a bean by using config file then red line willbe gone.
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.userRepo=userRepo;
        this.tokenrepo=tokenrepo;
    }
    public User signup(String email,String password,String name){
        User user=new User();
        user.setEmail(email);
        user.setHashedpassword(bCryptPasswordEncoder.encode(password));
        user.setName(name);
        user.set_verified(true);
        userRepo.save(user);
        return user;
    }
    public Token loginuser(String email, String password){
        Optional<User>optionalUser=userRepo.findByemail(email);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User saveduser=optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password,saveduser.getHashedpassword())){
            throw new RuntimeException("Password is not correct");
        }
        Token token=generateToken(saveduser);
        Token savedtoken=tokenrepo.save(token);
        return savedtoken;
    }

    private Token generateToken(User saveduser) {
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpirydate(expiryDate);
        //128 character alphanumeric string.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(saveduser);
        token.setActivestatus(true);
        return token;
    }
    public void logout(String tokenvalue){
        Optional<Token>optionalToken=tokenrepo.findByValue(tokenvalue);
        if(optionalToken.isEmpty()){
            throw new RuntimeException("Token is not valid");
        }
        Token updatedtoken=optionalToken.get();
        updatedtoken.setActivestatus(false);
        tokenrepo.save(updatedtoken);
    }
    public User validateToken(String tokenvalue){
        Optional<Token>optionalToken=tokenrepo.findByValueAndActivestatusAndExpirydateGreaterThan(tokenvalue,true,new Date());
        if(optionalToken.isEmpty()){
            return null;
        }
        return optionalToken.get().getUser();
    }
}
