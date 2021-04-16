package com.datealive.controller.admin;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.config.GiteeImgConfig;
import com.datealive.service.vo.GiteeImageVo;
import com.datealive.utils.RedisUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @ClassName: UpLoadImgController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/4/11  10:31
 */
@RestController
@RequestMapping("/admin")
@Transactional(rollbackFor = Exception.class)
public class UpLoadImgController {

    @Autowired
    RedisUtil redisUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "上传图片到Gitee",classification = 1)
    @RequiresAuthentication
    @PostMapping("/uploadImgtoGitee")
    @ResponseBody
    public Result uploadImg(@RequestParam("file") MultipartFile file) throws Exception {
        String originaFileName = file.getOriginalFilename();
        //上传图片不存在时
        if(originaFileName == null){
            return Result.error(ResultCode.Not_Found,"图片不存在");
        }

        String suffix = originaFileName.substring(originaFileName.lastIndexOf("."));
        //设置图片名字
        String fileName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + suffix;
        String paramImgFile = Base64.encode(file.getBytes());
        //设置转存到Gitee仓库参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImgConfig.ACCESS_TOKEN);
        paramMap.put("message", GiteeImgConfig.ADD_MESSAGE);
        paramMap.put("content", paramImgFile);

        //转存文件路径
        String targetDir = GiteeImgConfig.PATH + fileName;
        //设置请求路径
        String requestUrl = String.format(GiteeImgConfig.CREATE_REPOS_URL, GiteeImgConfig.OWNER,
                GiteeImgConfig.REPO_NAME, targetDir);

        String resultJson = HttpUtil.post(requestUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject==null || jsonObject.getObj("commit") == null) {
            return Result.error(ResultCode.Server_Error,"上传图片失败");
        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        //上传成功后需要更新redis
        redisUtil.del(StaticFinalCode.GITEE_IMAGE_PRO_FIX);
        return Result.success("上传图片成功",content.getObj("download_url"));
    }

    /**
     * 删除图片
     * @param imgPath
     * @return
     * @throws Exception
     */
    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除Gitee图床的图片",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/delImgFromGitee")
    @ResponseBody
    public Result delImg(@RequestParam(value = "imgPath") String imgPath) throws Exception {
        //路径不存在不存在时
        if(imgPath == null || "".equals(imgPath.trim())){
            return Result.error(ResultCode.Not_Found,"删除失败");
        }
        String path = imgPath.split("master/")[1];
        //上传图片不存在时
        if(path == null || "".equals(path.trim())){
            return Result.error(ResultCode.Not_Found,"图片不存在");
        }
        //设置请求路径
        String requestUrl = String.format(GiteeImgConfig.GET_IMG_URL, GiteeImgConfig.OWNER,
                GiteeImgConfig.REPO_NAME, path);


        //获取图片所有信息
        String resultJson = HttpUtil.get(requestUrl);

        JSONObject jsonObject = JSONUtil.parseObj(resultJson);

        if (jsonObject == null) {
            return Result.error(ResultCode.Server_Error,"Gitee服务器未响应");
        }
        //获取sha,用于删除图片
        String sha = jsonObject.getStr("sha");
        //设置删除请求参数
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImgConfig.ACCESS_TOKEN);
        paramMap.put("sha", sha);
        paramMap.put("message", GiteeImgConfig.DEl_MESSAGE);
        //设置删除路径
        requestUrl = String.format(GiteeImgConfig.DEL_IMG_URL, GiteeImgConfig.OWNER,
                GiteeImgConfig.REPO_NAME, path);

        //删除文件请求路径
        resultJson = HttpRequest.delete(requestUrl).form(paramMap).execute().body();
        HttpRequest.put(requestUrl).form(paramMap).execute().body();
        jsonObject = JSONUtil.parseObj(resultJson);
        //请求之后的操作
        if(jsonObject.getObj("commit") == null){
            return Result.error(ResultCode.Server_Error,"Gitee服务器未响应");
        }
        //如果删除成功的话 需要重新更新redis
        redisUtil.del(StaticFinalCode.GITEE_IMAGE_PRO_FIX);

        return Result.success("删除成功");
    }


    @RequiresAuthentication
    @GetMapping("/allImageByGiteeFromGitee")
    public Result getAllImageByGitee(){

        //保存图片的文件夹
        String path=GiteeImgConfig.PATH.substring(1,GiteeImgConfig.PATH.length()-1);
//        String path="img";
        //设置请求路径
        if(redisUtil.hasKey(StaticFinalCode.GITEE_IMAGE_PRO_FIX)){
            List<GiteeImageVo> RedisImages = (List<GiteeImageVo>) redisUtil.get(StaticFinalCode.GITEE_IMAGE_PRO_FIX);
            return Result.success("请求成功",RedisImages);
        }else{
            String requestUrl = String.format(GiteeImgConfig.GET_IMG_URL, GiteeImgConfig.OWNER,
                    GiteeImgConfig.REPO_NAME, path);
            //获取图片所有信息
            String resultJson = HttpUtil.get(requestUrl);
            List<GiteeImageVo> giteeImageVos = com.alibaba.fastjson.JSONObject.parseArray(resultJson, GiteeImageVo.class);
            redisUtil.set(StaticFinalCode.GITEE_IMAGE_PRO_FIX,giteeImageVos);
            if(giteeImageVos.size()==0){
                return Result.success("数据为空",null);
            }
            return Result.success("获取成功",giteeImageVos);
        }






    }

    /**
     * 集合分页
     *
     * @param resourceList 要分页的集合
     * @param pageIndex    页码
     * @param pageSize     每页条数
     * @return 分页后的集合
     */
    private static List<GiteeImageVo> getPageByList(List<GiteeImageVo> resourceList, int pageIndex, int pageSize,int totalPage) {

        List<GiteeImageVo> pageList = new ArrayList<>();
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if(pageIndex>totalPage){
            pageIndex=totalPage;
        }


        int size = resourceList.size();
        int pageCount = size / pageSize;
        int fromIndex = (pageIndex - 1) * pageSize;

        int toIndex = fromIndex + pageSize;
        if (toIndex >= size) {
            toIndex = size;
        }
        if (pageIndex > pageCount + 1) {
            fromIndex = 0;
            toIndex = 0;
        }

        pageList = resourceList.subList( fromIndex, toIndex );
        return pageList;
    }






}
