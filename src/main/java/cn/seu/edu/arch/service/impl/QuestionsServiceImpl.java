package cn.seu.edu.arch.service.impl;

import cn.seu.edu.arch.dao.QuestionsMapper;
import cn.seu.edu.arch.model.Questions;
import cn.seu.edu.arch.service.QuestionsService;
import cn.seu.edu.arch.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/07/21.
 */
@Service
@Transactional
public class QuestionsServiceImpl extends AbstractService<Questions> implements QuestionsService {
    @Resource
    private QuestionsMapper questionsMapper;

}
