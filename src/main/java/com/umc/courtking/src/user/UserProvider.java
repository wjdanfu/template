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

    public PostUserRes postUser(PostUserReq postUserReq){
        int userIdx= userDao.addUser(postUserReq);
        PostUserRes postUserRes = new PostUserRes(userIdx);
        return postUserRes;
    }
}
