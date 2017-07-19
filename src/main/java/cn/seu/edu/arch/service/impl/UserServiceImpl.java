package cn.seu.edu.arch.service.impl;

import cn.seu.edu.arch.core.AbstractService;
import cn.seu.edu.arch.service.UserService;
import cn.seu.edu.arch.dao.UserMapper;
import cn.seu.edu.arch.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/07/18.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

}
