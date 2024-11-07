package com.example.springUserAuth.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Role extends BaseModel {
    private  int  value;
    private Date expiryAt;
}
