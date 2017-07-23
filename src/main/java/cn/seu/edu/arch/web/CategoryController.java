package cn.seu.edu.arch.web;
import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.model.Category;
import cn.seu.edu.arch.service.CategoryService;
import cn.seu.edu.arch.service.impl.CategoryServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/07/21.
*/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryServiceImpl categoryService;

    @PostMapping("/add")
    public Result add(Category category) {
        categoryService.save(category);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        categoryService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Category category) {
        categoryService.update(category);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Category category = categoryService.findById(id);
        return ResultGenerator.genSuccessResult(category);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Category> list = categoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/listCategoryL2")
    public Result listCategoryL2() {
        List<Category> list = categoryService.getCategoryL2();
        return ResultGenerator.genSuccessResult(list);
    }

    @PostMapping("/getCategoryL3ByL2")
    public Result getCategoryL3ByL2(@RequestParam String parentId) {
        List<Category> list = categoryService.getCategoryL3ByL2(parentId);
        return ResultGenerator.genSuccessResult(list);
    }
}
