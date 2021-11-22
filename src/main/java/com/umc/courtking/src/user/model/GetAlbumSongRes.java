package com.umc.courtking.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter@Getter@AllArgsConstructor
public class GetAlbumSongRes {
    private int songIdx;
    private String title;
    private String singer;
    private String isTitleSong;
}
