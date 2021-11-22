package com.umc.courtking.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class GetAlbumRes {
    private int albumIdx;
    private String title;
    private String singer;
    private String coverImg;
}
