package com.datealive.service;

import com.datealive.common.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: UpLoadService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/24  18:51
 */
public interface UpLoadService {
    /**
     * 上传图片
     * @param request
     * @param file
     * @return
     */
    public Result UpLoadImage(HttpServletRequest request,MultipartFile file);
}
