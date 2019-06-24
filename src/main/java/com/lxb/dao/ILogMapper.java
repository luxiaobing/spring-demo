package com.lxb.dao;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import com.lxb.model.LogDo;
/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-06-22 20:33
 **/
@Repository
public interface ILogMapper {
    int saveLogDo(@Param("logDo") LogDo logDo);

}
