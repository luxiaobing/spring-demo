package com.lxb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-22 20:34
 **/
@Setter
@Getter
public class LogDo {
    private Long id;
    private String logContent;//日志内容
    private Date createTime;
    private Date updateTime;

}
