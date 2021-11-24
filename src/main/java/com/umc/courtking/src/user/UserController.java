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
    public BaseResponse<List<GetUserRes>> getUser(){
        List<GetUserRes> userRes = userProvider.getUser();
        return new BaseResponse<>(userRes);
    }

    @ResponseBody
    @PostMapping("/users")
    public BaseResponse<PostUserRes> postUser(@RequestBody PostUserReq postUserReq){
        PostUserRes postUserRes = userProvider.postUser(postUserReq);
        return new BaseResponse<>(postUserRes);
    }

}
