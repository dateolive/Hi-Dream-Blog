package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: GiteeImageVo
 * @Description: TODO
 * @author: datealive
 * @date: 2021/4/15  20:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiteeImageVo implements Serializable {


    private String type;
    private String name;
    private String sha;
    private String download_url;

}
