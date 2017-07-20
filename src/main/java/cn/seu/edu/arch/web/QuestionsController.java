package cn.seu.edu.arch.web;
import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.model.Questions;
import cn.seu.edu.arch.service.QuestionsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2017/07/20.
*/
@RestController
@RequestMapping("/questions")
public class QuestionsController {
    @Resource
    private QuestionsService questionsService;

    @PostMapping("/add")
    public Result add(Questions questions) {
        questionsService.save(questions);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        questionsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Questions questions) {
        questionsService.update(questions);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Questions questions = questionsService.findById(id);
        return ResultGenerator.genSuccessResult(questions);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Questions> list = questionsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
