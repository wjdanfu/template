package com.umc.courtking.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostUserLoginRes {
    private int userIdx;
    private String jwt;
}
