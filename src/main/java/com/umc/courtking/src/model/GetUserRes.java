package com.umc.courtking.src.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String ID;
    private String userName;
    private String password;
    private String email;
}
