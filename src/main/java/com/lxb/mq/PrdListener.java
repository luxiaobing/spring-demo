package com.lxb.mq;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-11-13 0:39
 **/
@Slf4j
@Component
public class PrdListener implements ChannelAwareMessageListener {
    @Transactional
    public void onMessage(Message message, Channel channel) {

        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//手动确认接收到消息
            log.info("message:{}", JSON.toJSON(new String(message.getBody(),"UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
