package com.site.springboot.core.controller.admin;

import com.site.springboot.core.config.Constants;
import com.site.springboot.core.util.Result;
import com.site.springboot.core.util.ResultGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class UploadController {

    @PostMapping({"/upload/file"})
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResultGenerator.genFailResult("请选择文件");
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        //使用 given pattern 和默认 FORMAT 语言环境的 default date format symbols 号构造一个 SimpleDateFormat。
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        //构造一个没有 characters 且 initial capacity 为 16 个 characters 的 string builder 。
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(Constants.FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);//此类专门包含对files、directories或其other types of files进行操作的static methods。
            //write:Writes bytes to a file. //
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result result = ResultGenerator.genSuccessResult();
        result.setData("/files/" + newFileName);
        return result;
    }

}
