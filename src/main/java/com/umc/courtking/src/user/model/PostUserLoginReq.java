package com.umc.courtking.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostUserLoginReq {
    private String email;
    private String password;
}
