package com.umc.courtking.src.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.umc.courtking.config.BaseException;
import com.umc.courtking.config.BaseResponse;
import com.umc.courtking.src.user.model.*;
import com.umc.courtking.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


import static com.umc.courtking.config.BaseResponseStatus.*;
import static com.umc.courtking.utils.ValidationRegex.*;


@RestController
public class UserController {

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/users")
    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userProvider.getUser();
        return userRes;
    }

    @ResponseBody
    @PostMapping("/users")
    public BaseResponse createUser(@RequestBody PostUserReq postUserReq) throws BaseException {
        //req에 입력하지 않은 경우
        if(postUserReq.getEmail() == null || postUserReq.getPassword()==null || postUserReq.getName()==null){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);

            return new BaseResponse();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/users/login")
    public BaseResponse<PostUserLoginRes> loginUser(@RequestBody PostUserLoginReq postUserLoginReq) throws BaseException {
        if(postUserLoginReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        try{
            PostUserLoginRes postUserLoginRes = userService.loginUser(postUserLoginReq);
            return new BaseResponse<>(postUserLoginRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/users/auto-login")
    public BaseResponse autologin() throws BaseException{
        try{
                int userIdx=jwtService.getUserIdx();
                GetAutoRes getAutoRes = userProvider.getAuto(userIdx);
                return new BaseResponse();

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/songs")
    public BaseResponse<Song> getSong() throws BaseException{
        try{
            Song song=new Song();
            List<GetSongRes> getSongRes = userProvider.getSong();
            song.setSongs(getSongRes);
            return new BaseResponse<>(song);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ResponseBody
    @GetMapping("/albums")
    public BaseResponse<Album> getAlbum() throws BaseException{
        try{
            Album album=new Album();
            List<GetAlbumRes> getAlbumRes = userProvider.getAlbum();
            album.setAlbums(getAlbumRes);
            return new BaseResponse<>(album);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/albums/{albumIdx}")
    public BaseResponse<List<GetAlbumSongRes>> getAlbumSong(@PathVariable("albumIdx") int albumIdx) throws BaseException{
        try{
            List<GetAlbumSongRes> getAlbumSongRes = userProvider.getAlbumSong(albumIdx);

            return new BaseResponse<>(getAlbumSongRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
