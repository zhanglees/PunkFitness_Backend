package com.healthclubs.pengke.controller;

import com.healthclubs.pengke.pojo.ResponseCode;
import com.healthclubs.pengke.pojo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
@Api("resource")
@RequestMapping("/api/resource")
public class ResourceController extends BaseController{

    /** 文件上传父目录 */
    @Value("${upload.file-space}")
    private String fileSpace;


    //创建自定义标签
    @ApiOperation(value = "/uploadFile", notes = "上传文件")
    @PostMapping(value = "/uploadFile")
    @Transactional
    public Result uploadFile(String userId, MultipartFile[] files) throws IOException {

       // String token = redisTemplate.opsForValue().get(USER_REDIS_SESSION + ":" + userId);
      //  if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
       //     return ResponseVo.error("用户id不能为空");
       // }
        //数据库相对路径
        String uploadPathDB = "/" + userId + "/face";

        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String imageName = UUID.randomUUID().toString();

        try {
            if (files != null && files.length > 0) {
                //文件名称
                String fileName = files[0].getOriginalFilename();
                fileName = imageName + (fileName.substring(fileName.lastIndexOf(".")));
                System.out.println("文件名称:" + fileName);
                if (!fileName.isEmpty()) {
                    //最终路径
                    String filePathFace = fileSpace + uploadPathDB + "/" + fileName;
                    System.out.println(filePathFace);
                    //数据库保存路径
                    uploadPathDB += ("/" + fileName);

                    File file = new File(filePathFace);
                    if (file.getParentFile() != null || file.getParentFile().isDirectory()) {
                        //创建父文件夹
                        file.getParentFile().mkdirs();
                    }

                    outputStream = new FileOutputStream(file);
                    inputStream = files[0].getInputStream();

                    IOUtils.copy(inputStream, outputStream);
                }
            } else {
                return getResult(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }

        //TODO这里你可以把图片地址保存到数据库等等

        return getResult(ResponseCode.SUCCESS_PROCESSED);
    }

}