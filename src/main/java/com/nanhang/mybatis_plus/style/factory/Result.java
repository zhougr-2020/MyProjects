package com.nanhang.mybatis_plus.style.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: immortal
 * @CreateDate: 2021/4/21 8:35
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private int code;

    private String message;

    private Object data;
}
