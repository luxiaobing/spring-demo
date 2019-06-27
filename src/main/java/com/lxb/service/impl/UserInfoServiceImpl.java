package com.lxb.service.impl;

import com.lxb.aspect.DataSource;
import com.lxb.aspect.LoginCheck;
import com.lxb.aspect.LoginCheckAspect;
import com.lxb.dao.ILogMapper;
import com.lxb.dao.IUserInfoMapper;
import com.lxb.model.LogDo;
import com.lxb.model.UserInfoDo;
import com.lxb.service.IUserInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-30 23:28
 **/
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(LoginCheckAspect.class);


    @Autowired
    private IUserInfoMapper userInfoMapper;
    @Autowired
    private ILogMapper logMapper;

    public UserInfoDo queryByUserCode(String userCode) {


        return userInfoMapper.queryByUserCode(userCode);
    }

    @Transactional
    @DataSource("ds_1")
    public int saveUserInfo() {

        UserInfoDo userInfoDo0 = new UserInfoDo();
        userInfoDo0.setUserCode("222");
        userInfoDo0.setCreateTime(new Date());
        userInfoDo0.setUpdateTime(new Date());
        userInfoDo0.setStatus(1);

        int saveDo0Num = userInfoMapper.saveUserInfoDo(userInfoDo0);
        System.out.println("saveDo0Num:{}" + saveDo0Num);


        logger.info("a=" + 1 / 0);

        UserInfoDo userInfoDo1 = new UserInfoDo();
        userInfoDo1.setUserCode("333");
        userInfoDo1.setCreateTime(new Date());
        userInfoDo1.setUpdateTime(new Date());
        userInfoDo1.setStatus(1);

        int saveDo1Num = userInfoMapper.saveUserInfoDo(userInfoDo1);
        System.out.println("saveDo1Num:{}" + saveDo1Num);


        return 0;
    }

    @LoginCheck(value = "1111")
    public void testLogin() {
        logger.info("hhh  testLogin begin");

    }

    @Transactional
    @DataSource("ds_2")
    public int saveLog(){
        LogDo logDo = new LogDo();
        logDo.setLogContent("hhhh0");
        logDo.setCreateTime(new Date());
        logDo.setUpdateTime(new Date());
        int saveNum = logMapper.saveLogDo(logDo);

        //logger.info("a=" + 1 / 0);
        LogDo logDo2 = new LogDo();
        logDo2.setLogContent("hhhh1");
        logDo2.setCreateTime(new Date());
        logDo2.setUpdateTime(new Date());
        int saveLogDo2Num = logMapper.saveLogDo(logDo);
        logger.info("saveLogDo2Num=" + saveLogDo2Num);

        return saveNum;
    }
    @Transactional
    public void saveTest(){
        saveLog();
        //logger.info("a=" + 1 / 0);
        saveUserInfo();
    }
}