package cn.seu.edu.arch.service.impl;

import cn.seu.edu.arch.core.AbstractService;
import cn.seu.edu.arch.dao.CategoryMapper;
import cn.seu.edu.arch.model.Category;
import cn.seu.edu.arch.model.User;
import cn.seu.edu.arch.service.CategoryService;
import cn.seu.edu.arch.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/07/21.
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    public List<String> getCategoryL1(){
        ArrayList<String> ret = new ArrayList<>();
        ret.add("默认分类");
        return ret;
    }

    public List<Category> getCategoryL2(){
        Condition condition = new Condition(Category.class);
        condition.createCriteria()
                .andEqualTo("level", 1);
        return categoryMapper.selectByCondition(condition);
    }

    public List<Category> getCategoryL3ByL2(String l2CategoryId){
        Condition condition = new Condition(Category.class);
        condition.createCriteria()
                .andEqualTo("level", 2)
                .andEqualTo("parent", l2CategoryId);
        return categoryMapper.selectByCondition(condition);
    }



}
