package com.lxc.learn.mq.core;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxc.learn.common.util.core.BaseDto;
import java.nio.charset.Charset;
import java.util.Objects;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class MqAgent
        implements InitializingBean, DisposableBean
{
    private static final Logger log = LoggerFactory.getLogger(MqAgent.class);
    private DefaultMQProducer producer;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String charset = "UTF-8";

    private MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash();

    public SendResult syncSend(String topic, String tag, BaseDto message)
    {
        return syncSend(topic, tag, message, this.producer.getSendMsgTimeout());
    }

    public SendResult syncSend(String topic, String tag, BaseDto message, long timeout)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag))) {
            log.info("syncSend failed.topic:{} tag:{} message:{} can't not null", new Object[] { topic, tag, message });
            throw new IllegalArgumentException("`message` and `message.payload` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            long now = System.currentTimeMillis();
            Message rocketMsg = convertToRocketMsg(destination, message);
            SendResult sendResult = this.producer.send(rocketMsg, timeout);
            long costTime = System.currentTimeMillis() - now;
            log.debug("send message cost: {} ms, msgId:{}", Long.valueOf(costTime), sendResult.getMsgId());
            return sendResult;
        } catch (Exception e) {
            log.info("syncSend failed. destination:{}, message:{} ", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public SendResult syncSendOrderly(String topic, String tag, BaseDto message, String hashKey)
    {
        return syncSendOrderly(topic, tag, message, hashKey, this.producer.getSendMsgTimeout());
    }

    public SendResult syncSendOrderly(String topic, String tag, BaseDto message, String hashKey, long timeout)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag)) || (Objects.isNull(hashKey))) {
            log.info("syncSendOrderly failed.topic:{} tag:{} message:{} hashKey:{} can't not null", new Object[] { topic, tag, message, hashKey });

            throw new IllegalArgumentException("`message` and `message.payload` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            long now = System.currentTimeMillis();
            Message rocketMsg = convertToRocketMsg(destination, message);
            SendResult sendResult = this.producer.send(rocketMsg, this.messageQueueSelector, hashKey, timeout);
            long costTime = System.currentTimeMillis() - now;
            log.debug("send message cost: {} ms, msgId:{}", Long.valueOf(costTime), sendResult.getMsgId());
            return sendResult;
        } catch (Exception e) {
            log.info("syncSendOrderly failed. destination:{}, message:{} ", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void asyncSend(String topic, String tag, BaseDto message, SendCallback sendCallback, long timeout)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag)) || (Objects.isNull(sendCallback))) {
            log.info("asyncSend failed.topic:{} tag:{} message:{} sendCallback:{} can't not null", new Object[] { topic, tag, message, sendCallback });

            throw new IllegalArgumentException("`message` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            Message rocketMsg = convertToRocketMsg(destination, message);
            this.producer.send(rocketMsg, sendCallback, timeout);
        } catch (Exception e) {
            log.info("asyncSend failed. destination:{}, message:{} ", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void asyncSendOrderly(String topic, String tag, BaseDto message, String hashKey, SendCallback sendCallback, long timeout)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag)) || (Objects.isNull(sendCallback))) {
            log.info("asyncSendOrderly failed.topic:{} tag:{} message:{} sendCallback:{} can't not null", new Object[] { topic, tag, message, sendCallback });

            throw new IllegalArgumentException("`message` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            Message rocketMsg = convertToRocketMsg(destination, message);
            this.producer.send(rocketMsg, this.messageQueueSelector, hashKey, sendCallback, timeout);
        } catch (Exception e) {
            log.info("asyncSendOrderly failed. destination:{}, message:{} ", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void asyncSendOrderly(String topic, String tag, BaseDto message, String hashKey, SendCallback sendCallback)
    {
        asyncSendOrderly(topic, tag, message, hashKey, sendCallback, this.producer.getSendMsgTimeout());
    }

    public void sendOneWay(String topic, String tag, BaseDto message)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag))) {
            log.info("sendOneWay failed.topic:{} tag:{} message:{}  can't not null", new Object[] { topic, tag, message });
            throw new IllegalArgumentException("`message` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            Message rocketMsg = convertToRocketMsg(destination, message);
            this.producer.sendOneway(rocketMsg);
        } catch (Exception e) {
            log.info("sendOneWay failed. destination:{}, message:{} ", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public SendResult tranSend(String destination, BaseDto message, long timeout) throws MQClientException {
        return null;
    }

    public void sendOneWayOrderly(String topic, String tag, BaseDto message, String hashKey)
    {
        if ((Objects.isNull(message)) || (Objects.isNull(topic)) || (Objects.isNull(tag))) {
            log.info("sendOneWayOrderly failed.topic:{} tag:{} message:{}  can't not null", new Object[] { topic, tag, message });
            throw new IllegalArgumentException("`message` cannot be null");
        }
        String destination = topic + ":" + tag;
        try {
            Message rocketMsg = convertToRocketMsg(destination, message);
            this.producer.sendOneway(rocketMsg, this.messageQueueSelector, hashKey);
        } catch (Exception e) {
            log.info("sendOneWayOrderly failed. destination:{}, message:{}", destination, message);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void afterPropertiesSet() throws Exception
    {
        Assert.notNull(this.producer, "Property 'defaultProducer' is required");
        this.producer.start();
    }

    protected void doSend(String topic, String tag, BaseDto message) {
        syncSend(topic, tag, message);
    }

    private Message convertToRocketMsg(String destination, BaseDto message)
    {
        byte[] payloads = JSON.toJSONString(message).getBytes(Charset.forName(this.charset));

        String[] tempArr = destination.split(":", 2);
        String topic = tempArr[0];
        String tags = "";
        if (tempArr.length > 1) {
            tags = tempArr[1];
        }
        Message rocketMsg = new Message(topic, tags, payloads);

        return rocketMsg;
    }

    public void destroy()
    {
        if (Objects.nonNull(this.producer))
            this.producer.shutdown();
    }

    public void setProducer(DefaultMQProducer producer)
    {
        this.producer = producer; }
    public DefaultMQProducer getProducer() { return this.producer; }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper; }
    public ObjectMapper getObjectMapper() { return this.objectMapper; }

    public String getCharset() {
        return this.charset; }
    public void setCharset(String charset) { this.charset = charset; }

    public MessageQueueSelector getMessageQueueSelector() {
        return this.messageQueueSelector; }
    public void setMessageQueueSelector(MessageQueueSelector messageQueueSelector) { this.messageQueueSelector = messageQueueSelector;
    }
}

