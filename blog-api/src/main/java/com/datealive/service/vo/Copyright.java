package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Copyright
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copyright implements Serializable {
    private String title;
    private String siteName;
}
