package cn.seu.edu.arch.service.impl;

import cn.seu.edu.arch.dao.CollectionsMapper;
import cn.seu.edu.arch.model.Collections;
import cn.seu.edu.arch.service.CollectionsService;
import cn.seu.edu.arch.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/07/23.
 */
@Service
@Transactional
public class CollectionsServiceImpl extends AbstractService<Collections> implements CollectionsService {
    @Resource
    private CollectionsMapper collectionsMapper;

}
