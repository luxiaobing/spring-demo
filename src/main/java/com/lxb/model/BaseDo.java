package com.lxb.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-30 23:20
 **/
@Getter
@Setter
public class BaseDo {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
