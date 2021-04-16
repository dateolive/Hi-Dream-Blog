package com.datealive.test;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.datealive.config.GiteeImgConfig;
import com.datealive.utils.UserUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @ClassName: testTest
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/18  0:00
 */
@SpringBootTest
class testTest {


    @Test
    public void test(){
        String path=GiteeImgConfig.PATH.substring(1,GiteeImgConfig.PATH.length()-1);
        System.out.println(path);
    }




}