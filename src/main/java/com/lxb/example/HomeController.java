package com.lxb.example;

import com.alibaba.fastjson.JSON;
import com.lxb.api.UserService;
import com.lxb.model.UserInfoDo;
import com.lxb.service.IUserInfoService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

import com.lxb.utils.NewExcelExportUtil;


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
    @Resource
    private UserService userService;

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

    @RequestMapping("/downloadExcel")
    public  String downloadExcel(HttpServletRequest request, final HttpServletResponse response){
        String fileName = "test.xls";
        try {
            OutputStream out = new FileOutputStream(fileName);
            List<List<String>> data1 = new ArrayList<List<String>>();//sheet1 data
            for (int i = 1; i < 5; i++) {
                List rowData = new ArrayList();
                rowData.add(String.valueOf(i));
                rowData.add("data1");
                data1.add(rowData);
            }
            List<List<String>> data2 = new ArrayList<List<String>>();//sheet2 data
            for (int i = 1; i < 5; i++) {
                List rowData = new ArrayList();
                rowData.add(String.valueOf(i));
                rowData.add("data2");
                data2.add(rowData);
            }
            List<List<String>> data3 = new ArrayList<List<String>>();//sheet3 data
            for (int i = 1; i < 5; i++) {
                List rowData = new ArrayList();
                rowData.add(String.valueOf(i));
                rowData.add("data3");
                data3.add(rowData);
            }

            List<List<String>> data4 = new ArrayList<List<String>>();//sheet3 data
            for (int i = 1; i < 5; i++) {
                List rowData = new ArrayList();
                rowData.add(String.valueOf(i));
                rowData.add("data3");
                data4.add(rowData);
            }
            String[] firstHeaders1 = {"X月X日-X日xxx（单位名称）酒店账单明细表"};
            String[] secHeaders1 = {"X月X日-X日xxx（单位名称）酒店账单明细表"};
            String[] thirdHeaders1 = {"X月X日-X日xxx（单位名称）酒店账单明细表"};

            String[] firstHeaders2 = { "航班号","舱位","乘机日期/时间","票价","改期费","基建费","燃油费","票总价","保险","退票费","服务费","客人应收","票状态"};
            String[] secHeaders2 = { "TR单号","订单号","出票日期","退票日期","部门","姓名","行程","车次","席别","乘车日期/时间","票价","改签差价","改签手续费","票总价","退票费","保险","系统使用费","客人应收"};
            String[] thirdHeaders2 = { "TR单号","订单号","预订日期","部门","姓名","酒店名称","房型","入住日期","离店日期","间夜","平均单价","总价","服务费","客人应收","支付类型"};

            NewExcelExportUtil eeu = new NewExcelExportUtil();
            HSSFWorkbook workbook = new HSSFWorkbook();
            eeu.exportExcel(workbook, 0, "机票", firstHeaders1, firstHeaders2, data1, out);
            eeu.exportExcel(workbook, 1, "火车票", secHeaders1, secHeaders2, data2, out);
            eeu.exportExcel(workbook, 2, "酒店", thirdHeaders1, thirdHeaders2, data3, out);
            eeu.exportExcel4Gather(workbook, 3, "汇总表", data4, out);
            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(out);

            /*final StringBuffer filePath = new StringBuffer();
            filePath.append(tmpOrderFolder).append(String.format("sheet_%d.xls", System.currentTimeMillis()));*/
            eeu.download(fileName, response);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }

    //映射一个action
    @RequestMapping("/queryUserService")
    public  String queryUserService(HttpServletRequest request){
        log.info("start queryUserService");
        String b = userService.queryUserCodeByDubbo("aaa");
        log.info("end queryUserService,b:{}", b);

        return "aa";
    }


    public static void main(String[] args) {
        System.out.println("hh");
        System.out.println("hh1");

        System.out.println("hh2");


        System.out.println("hh3");
        System.out.println("hh4");



}
