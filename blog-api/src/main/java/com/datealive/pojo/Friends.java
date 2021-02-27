package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: Friends
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  20:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friends {

    private Integer link_id;
    @NotBlank(message = "站点名称不能为空")
    private String link_name;
    @NotBlank(message = "站点url不能为空")
    private String link_url;
    private String link_info;
    private String avatar;
    private Integer status;
}
