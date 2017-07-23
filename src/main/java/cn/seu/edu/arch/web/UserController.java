package cn.seu.edu.arch.web;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.jwt.AuthTokenDTO;
import cn.seu.edu.arch.jwt.AuthTokenDetails;
import cn.seu.edu.arch.jwt.JsonWebTokenUtility;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.impl.UserServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by CodeGenerator on 2017/07/20.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserServiceImpl userService;

    @Autowired
    private JsonWebTokenUtility tokenService;

    //@Autowired
    //private JsonWebTokenAuthenticationProvider tokenProvider;

    @PostMapping("/add")
    public Result add(User user) {
        userService.save(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(User user, HttpServletRequest request) {
        String userId = tokenService.parseAndGetUserId(tokenService.getToken(request));
        if (userId == null || userId.length() == 0) {
            return ResultGenerator.genFailResult("为登录用户");
        }
        user.setUserId(Integer.valueOf(userId));
        userService.update(user);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/updateProfile")
    public Result updateProfile(@RequestParam String profile, HttpServletRequest request) {
        String userId = tokenService.parseAndGetUserId(tokenService.getToken(request));
        if (userId == null || userId.length() == 0) {
            return ResultGenerator.genFailResult("为登录用户");
        }
        JSONObject object = (JSONObject) JSON.parse(profile);
        String phoneNumber = "";
        String oldSchool = "";
        String targetSchool = "";
        if (object.get("phoneNumber") != null) {
            phoneNumber = String.valueOf(object.get("phoneNumber"));
        }
        if (object.get("oldSchool") != null) {
            oldSchool = String.valueOf(object.get("oldSchool"));
        }
        if (object.get("targetSchool") != null) {
            targetSchool = String.valueOf(object.get("targetSchool"));
        }
        log.info("phoneNumber:\t" + phoneNumber);
        log.info("oldSchool:\t" + oldSchool);
        log.info("targetSchool:\t" + targetSchool);
        User user = new User();
        user.setUserId(Integer.valueOf(userId));
        user.setPhoneNumber(phoneNumber);
        user.setOldSchool(oldSchool);
        user.setTargetSchool(targetSchool);
        userService.update(user);
        return ResultGenerator.genSuccessResult("用户信息更新成功");
    }


    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        String token;
        User u = null;
        if (user.getUsername() != null && user.getPassword() != null) {
            u = userService.getUserByNamePassword(user.getUsername(), user.getPassword());
        }
        if (u != null) {
            authTokenDTO.setUserId(u.getUserId());
            token = tokenService.generateJSONWebToken(String.valueOf(u.getUserId()));
        } else {
            authTokenDTO.setUserId(-1);
            token = tokenService.generateJSONWebToken("-1");
        }

        authTokenDTO.setToken(token);
        return ResultGenerator.genSuccessResult(authTokenDTO);
    }
}
