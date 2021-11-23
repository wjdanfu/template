package com.umc.courtking.src.user;

import com.umc.courtking.config.BaseException;
import com.umc.courtking.config.secret.Secret;
import com.umc.courtking.src.user.model.*;
import com.umc.courtking.utils.AES128;
import com.umc.courtking.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;

import static com.umc.courtking.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
            if (userProvider.checkName(postUserReq.getName()) == 1) {
                throw new BaseException(POST_USERS_EXISTS_NAME);
            }
            if (userProvider.checkEmail(postUserReq.getEmail()) == 1) {
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            }

            String pwd;
            try {
                //암호화
                pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
                postUserReq.setPassword(pwd);
            } catch (Exception ignored) {
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }

            int userIdx = userDao.createUser(postUserReq);

            return new PostUserRes(userIdx);

    }

    @Transactional
    public PostUserLoginRes loginUser(PostUserLoginReq postUserLoginReq) throws BaseException {

        int userIdx = userProvider.checkAccount(postUserLoginReq.getEmail(), postUserLoginReq.getPassword());
        userDao.recordLog(userIdx, "I");
        //jwt발급
        String jwt = jwtService.createJwt(userIdx);

        return new PostUserLoginRes(userIdx,jwt);
    }

}