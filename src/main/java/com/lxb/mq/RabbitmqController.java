package com.lxb.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import javax.annotation.Resource;

import java.util.*;

import org.springframework.amqp.core.AmqpTemplate;


/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-11-10 15:24
 **/
@Controller
@RequestMapping("/rabbitMq")
@Slf4j
public class RabbitmqController {
    @Resource
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/rabbitmqSend")
    public Map<String, Object> rabbitmqSend(HttpServletRequest request) {

        log.info("start sendMq");
        String msg = request.getParameter("msg");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", msg);
        log.info("rabbitmqSend,msg:{}", msg);
        amqpTemplate.convertAndSend("com.yingmu.mq.test", msg);
        log.info("end sendMq");

        return map;
    }

}
