package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: MomentUpdStatus
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/25  12:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentUpdStatus implements Serializable {
    private Integer id;
    private Integer isPublish;
}
