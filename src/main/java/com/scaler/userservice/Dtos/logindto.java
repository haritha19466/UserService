package com.scaler.userservice.Dtos;

import com.scaler.userservice.Models.Token;
import lombok.Data;

import java.util.Date;

@Data
public class logindto {
    private Date expiry;
    private boolean active;

    private String value;
    public static logindto from(Token t){
        logindto loginDtoobj = new logindto();
        loginDtoobj.setExpiry(t.getExpirydate());
        loginDtoobj.setActive(t.isActivestatus());
        loginDtoobj.setValue(t.getValue());
        return loginDtoobj;
    }
}
