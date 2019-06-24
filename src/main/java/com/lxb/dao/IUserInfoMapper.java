package com.lxb.dao;

import com.lxb.model.UserInfoDo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-30 22:59
 **/
@Repository
public interface IUserInfoMapper {
    UserInfoDo queryByUserCode(@Param("userCode") String userCode);
    int saveUserInfoDo(@Param("userInfoDo") UserInfoDo userInfoDo);
}
