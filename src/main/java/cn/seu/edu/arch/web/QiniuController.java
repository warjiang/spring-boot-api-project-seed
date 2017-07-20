package cn.seu.edu.arch.web;

import cn.seu.edu.arch.core.Result;
import cn.seu.edu.arch.core.ResultGenerator;
import cn.seu.edu.arch.model.User;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by CodeGenerator on 2017/07/20.
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController {
    @Value("${qiniu.accessKey}")
    String accessKey;
    @Value("${qiniu.secretKey}")
    String secretKey;
    @Value("${qiniu.bucket}")
    String bucket;

    @GetMapping("/generateUpToken")
    public Result generateUpToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        HashMap<String, String> ret = new HashMap<>();
        ret.put("upToken", upToken);
        return ResultGenerator.genSuccessResult(ret);
    }
}
