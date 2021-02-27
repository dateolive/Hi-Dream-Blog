package com.datealive.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: SiteSetting
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SiteSetting {

    private Integer id;
    private String name_en;
    private String name_zh;
    private String value;
    /**
     * 1基础设置，2页脚徽标，3资料卡，4友链信息
     */
    private Integer type;
}
