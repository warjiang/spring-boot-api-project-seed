package cn.seu.edu.arch.web;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.jwt.AuthTokenDTO;
import cn.seu.edu.arch.jwt.AuthTokenDetails;
import cn.seu.edu.arch.jwt.JsonWebTokenUtility;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Result update(User user) {
        userService.update(user);
        return ResultGenerator.genSuccessResult();
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
