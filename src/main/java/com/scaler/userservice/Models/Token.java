package com.scaler.userservice.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.util.Date;
import java.util.List;
@Data
@Entity
public class Token extends BaseModel{
    private Date expirydate;
    private String value;
    private boolean activestatus;
    @ManyToOne
    private User user;
}
