package com.lxb.example;

import com.alibaba.fastjson.JSON;
import com.lxb.model.UserInfoDo;
import com.lxb.service.IUserInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import lombok.extern.slf4j.Slf4j;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2017-12-31 1:06
 **/
@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {
    @Autowired
    private IUserInfoService userInfoService;

    //映射一个action
    @RequestMapping("/index")
    public  String index(HttpServletRequest request){

        String name = request.getParameter("username");
        System.out.println(name);
        //输出日志文件
        System.out.println("aaa nn");
        log.info("the first jsp pages");
        System.out.println("bbb nn");

        Hashtable h = new Hashtable();
        //返回一个index.jsp这个视图
        return "index";
    }


    @RequestMapping("/queryUserCode")
    public  String queryUserCode(HttpServletRequest request){

        String userCode = request.getParameter("userCode");
        System.out.println(userCode);
        log.info("userCode:{}", JSON.toJSON(userCode));
        UserInfoDo userInfoDo = userInfoService.queryByUserCode(userCode);
        log.info("userInfoDo:{}", JSON.toJSON(userInfoDo));
        //返回一个index.jsp这个视图
        return "index";
    }
    @RequestMapping("/saveUserInfoDo")
    public  String saveUserInfoDo(HttpServletRequest request){

        log.info("start saveUserCode");
        try {
            int saveNum = userInfoService.saveUserInfo();
        } catch (Exception e) {
            log.info("end saveUserCode:{}",e);
        }
        log.info("end saveUserCode");
        //返回一个index.jsp这个视图
        return "";
    }

    @RequestMapping("/testLogin")
    public  String testLogin(HttpServletRequest request){

        log.info("start testLogin");
        try {
            userInfoService.testLogin();
        } catch (Exception e) {
            log.info("end testLogin:{}",e);
        }
        log.info("end testLogin");
        //返回一个index.jsp这个视图
        return "";
    }


    @RequestMapping("/saveLogDo")
    public  String saveLogDo(HttpServletRequest request){

        log.info("start saveLogDo");
        try {
            int saveNum = userInfoService.saveLog();
            log.info("saveLogDo,saveNum:{}", saveNum);
        } catch (Exception e) {
            log.info("end saveLogDo:{}",e);
        }
        log.info("end saveLogDo");
        //返回一个index.jsp这个视图
        return "";
    }

    @RequestMapping("/saveTest")
    public  String saveTest(HttpServletRequest request){

        log.info("start saveTest");
        try {
            userInfoService.saveTest();
        } catch (Exception e) {
            log.info("end saveTest:{}",e);
        }
        log.info("end saveTest");
        //返回一个index.jsp这个视图
        return "";
    }
}
