package com.lxb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-30 23:00
 **/
@Setter
@Getter
public class UserInfoDo {
    private Long id;
    private String userCode;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
