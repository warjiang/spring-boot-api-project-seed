package cn.seu.edu.arch.web;
import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.model.Collections;
import cn.seu.edu.arch.service.CollectionsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/07/23.
*/
@RestController
@RequestMapping("/collections")
public class CollectionsController {
    @Resource
    private CollectionsService collectionsService;

    @PostMapping("/add")
    public Result add(Collections collections) {
        collectionsService.save(collections);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        collectionsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Collections collections) {
        collectionsService.update(collections);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Collections collections = collectionsService.findById(id);
        return ResultGenerator.genSuccessResult(collections);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Collections> list = collectionsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
