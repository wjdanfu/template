package com.umc.courtking.src.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Album {
    private List<GetAlbumRes> albums;
}
