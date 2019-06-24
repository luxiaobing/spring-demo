package com.lxb.service;

import com.lxb.model.UserInfoDo;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-30 23:27
 **/


public interface IUserInfoService {
    UserInfoDo queryByUserCode(String userCode);

    int saveUserInfo();

    void testLogin();

    int saveLog();

    void saveTest();

}
