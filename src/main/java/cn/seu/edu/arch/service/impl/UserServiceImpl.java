package cn.seu.edu.arch.service.impl;

import cn.seu.edu.arch.dao.UserMapper;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.UserService;
import cn.seu.edu.arch.core.AbstractService;
import cn.seu.edu.arch.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/07/20.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    public User getUserByNamePassword(String username, String password) {
        Condition condition = new Condition(User.class);
        condition.createCriteria()
                .andEqualTo("username", username)
                .andEqualTo("password", Utils.getMD5(password));
        List<User> list = userMapper.selectByCondition(condition);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

}
