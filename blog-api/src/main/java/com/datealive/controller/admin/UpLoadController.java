package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.Result;
import com.datealive.service.UpLoadService;
import com.datealive.shiro.MyProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: UpLoadController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/8  16:44
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class UpLoadController {

    @Autowired
    UpLoadService upLoadService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @LogAnno(operateType="POST",description = "上传图片",classification = 1)
    @PostMapping("/uploadImg")
    public Result UpLoadImg(HttpServletRequest request, @RequestParam("blog_img") MultipartFile file){
        Result result = upLoadService.UpLoadImage(request, file);
        return result;
    }

}
