package com.umc.courtking.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserLoginPWRes {
    private String email;
    private String password;
    private int userIdx;
}
