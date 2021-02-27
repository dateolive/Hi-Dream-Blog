package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: FriendsUpdStatus
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/25  12:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendsUpdStatus implements Serializable {

    private Integer link_id;
    private Integer status;
}
