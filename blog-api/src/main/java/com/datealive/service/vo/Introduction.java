package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Introduction
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Introduction implements Serializable {

    private String avatar;
    private String name;
    private String github;
    private String qq;
    private String bilibili;
    private String netease;
    private String email;
    private String musicId;
    private String backGroundImage;

    private List<String> rollText = new ArrayList<>();
    private List<Favorite> favorites = new ArrayList<>();
}
