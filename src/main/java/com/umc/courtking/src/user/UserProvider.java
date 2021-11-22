package com.umc.courtking.src.user;


import com.umc.courtking.config.BaseException;
import com.umc.courtking.config.secret.Secret;
import com.umc.courtking.src.user.model.*;
import com.umc.courtking.utils.AES128;
import com.umc.courtking.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

import static com.umc.courtking.config.BaseResponseStatus.*;

@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userDao.userRes();

        return userRes;
    }


    public int checkName(String name){
        return userDao.checkName(name);
    }
    public int checkEmail(String email){
        return userDao.checkEmail(email);
    }

    public int checkAccount(String email,String password) throws BaseException {
        if(userDao.checkEmail(email)!=1){
            throw new BaseException(POST_USERS_NONEXIST_ACCOUNT);
        }

        PostUserLoginPWRes postUserLoginPWRes = userDao.checkAccount(email); // 여기서 잘 못받아오면 에러나옴 그러므로 미리 위에서 체크하는 것임

        String realpw;
        try{
            realpw = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(postUserLoginPWRes.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        //System.out.println("테스트2:"+postUserLoginPWRes.getEmailId());
        //System.out.println("테스트3:"+password);
        //System.out.println("테스트4:"+realpw);

        if(postUserLoginPWRes.getEmail().equals(email) && realpw.equals(password)){
            return postUserLoginPWRes.getUserIdx();
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
    public String checkLog(int userIdx){
        String logStatus = userDao.checkLog(userIdx);
        return logStatus;
    }
    public int checkLogExist(int userIdx){
        int exist = userDao.checkLogExist(userIdx);
        return exist;
    }

    public GetAutoRes getAuto(int userIdx) throws BaseException {
        GetAutoRes getAutoRes = new GetAutoRes();
        if (userDao.getAuto(userIdx)=='I') {
            getAutoRes.setStatus(true);
        }
        else{
            getAutoRes.setStatus(false);
        }
        return getAutoRes;
    }

    public List<GetSongRes> getSong() throws BaseException{
        List<GetSongRes> getSongRes = userDao.getSong();
        return getSongRes;
    }

    public List<GetAlbumRes> getAlbum() throws BaseException{
        List<GetAlbumRes> getAlbumRes=userDao.getAlbum();
        return getAlbumRes;
    }
    public List<GetAlbumSongRes> getAlbumSong(int albumIdx) throws BaseException{
        List<GetAlbumSongRes> getAlbumSongRes=userDao.getAlbumSong(albumIdx);
        return getAlbumSongRes;
    }


}
