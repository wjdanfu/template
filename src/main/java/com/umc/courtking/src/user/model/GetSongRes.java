package com.umc.courtking.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetSongRes {
    private int songIdx;
    private int albumIdx;
    private String singer;
    private String title;
    private String coverImg;
}
