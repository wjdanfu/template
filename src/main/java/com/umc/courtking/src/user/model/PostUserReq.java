package com.umc.courtking.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostUserReq {
    private String ID;
    private String userName;
    private String password;
    private String email;
}
