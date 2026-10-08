package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utlils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        // 把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        // 保证文件名唯一 防止文件覆盖
        String filename = UUID.randomUUID().toString() +originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("D:\\SpringBoot\\ideaProjects\\files\\" + filename));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
