//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.mq.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class DefaultMqListenerContainer implements InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(DefaultMqListenerContainer.class);
    private long suspendCurrentQueueTimeMillis = 1000L;
    private int delayLevelWhenNextConsume = 0;
    private String consumerGroup;
    private String nameServer;
    private String topic;
    private ConsumeMode consumeMode;
    private String selectorExpress;
    private MessageModel messageModel;
    private int consumeThreadMax;
    private String charset;
    private ObjectMapper objectMapper;
    private boolean started;
    private MqSingleListener mqSingleListener;
    private MqMultiListener mqMultiListener;
    private DefaultMQPushConsumer consumer;
    private Class msgClassType;
    private JavaType msgJavaType;
    private String consumeName;
    private Integer batchMaxSize;
    private String serverPort;

    public DefaultMqListenerContainer() {
        this.consumeMode = ConsumeMode.CONCURRENTLY;
        this.selectorExpress = "*";
        this.messageModel = MessageModel.CLUSTERING;
        this.consumeThreadMax = 64;
        this.charset = "UTF-8";
        this.objectMapper = new ObjectMapper();
        this.batchMaxSize = 1;
    }

    public void destroy() {
        this.setStarted(false);
        if (Objects.nonNull(this.consumer)) {
            this.consumer.shutdown();
        }

        log.info("--------------container destroyed, {}", this.toString());
    }

    public synchronized void start() throws MQClientException {
        if (this.isStarted()) {
            throw new IllegalStateException("container already started. " + this.toString());
        } else {
            this.initRocketMQPushConsumer();
            this.parseAndFillMessageType();
            this.consumer.start();
            this.setStarted(true);
            log.info("--------------started container: {}", this.toString());
        }
    }

    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    private Object doConvertMessage(MessageExt messageExt) {
        if (Objects.equals(this.msgClassType, MessageExt.class)) {
            return messageExt;
        } else {
            String str = new String(messageExt.getBody(), Charset.forName(this.charset));

            try {
                if (this.msgJavaType != null) {
                    return this.objectMapper.readValue(str, this.msgJavaType);
                }

                if (this.msgClassType != null) {
                    return this.objectMapper.readValue(str, this.msgClassType);
                }
            } catch (Exception var4) {
                log.info("convert failed. receive info:{}, convert type:{}", str, this.msgClassType != null ? this.msgClassType : this.msgJavaType);
                log.info("", var4);
            }

            return null;
        }
    }

    private void parseAndFillMessageType() {
        Type[] interfaces = null;
        if (this.mqSingleListener != null) {
            interfaces = this.mqSingleListener.getClass().getGenericInterfaces();
        } else if (this.mqMultiListener != null) {
            interfaces = this.mqMultiListener.getClass().getGenericInterfaces();
        }

        if (Objects.nonNull(interfaces)) {
            Type[] var2 = interfaces;
            int var3 = interfaces.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Type type = var2[var4];
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType)type;
                    if (Objects.equals(parameterizedType.getRawType(), MqSingleListener.class) || Objects.equals(parameterizedType.getRawType(), MqMultiListener.class)) {
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        if (!(actualTypeArguments[0] instanceof ParameterizedType) && Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            this.msgClassType = (Class)actualTypeArguments[0];
                        } else if (actualTypeArguments[0] instanceof ParameterizedType && Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                            ParameterizedType messageType = (ParameterizedType)actualTypeArguments[0];
                            this.msgJavaType = this.getCollectionType((Class)messageType.getRawType(), (Class)messageType.getActualTypeArguments()[0]);
                        }
                    }
                }
            }

        }
    }

    public JavaType getCollectionType(Class<?> collectionClass, Class... elementClasses) {
        return this.objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    private void initRocketMQPushConsumer() throws MQClientException {
        Assert.notNull(this.mqSingleListener != null || this.mqMultiListener != null, "Property 'MqSingleListener' or 'MqMultiListener'  is required");
        Assert.notNull(this.consumerGroup, "Property 'consumerGroup' is required");
        Assert.notNull(this.nameServer, "Property 'nameServer' is required");
        Assert.notNull(this.topic, "Property 'topic' is required");
        DefaultMQPushConsumer defaultConsumer = new DefaultMQPushConsumer();
        defaultConsumer.setNamesrvAddr(this.getNameServer());
        defaultConsumer.setConsumeThreadMax(this.getConsumeThreadMax());
        defaultConsumer.setConsumeThreadMin(this.getConsumeThreadMax());
        defaultConsumer.setConsumerGroup(this.getConsumerGroup());
        defaultConsumer.setVipChannelEnabled(false);
        defaultConsumer.subscribe(this.topic, this.selectorExpress);
        defaultConsumer.setMessageModel(this.messageModel);
        defaultConsumer.setConsumeMessageBatchMaxSize(this.batchMaxSize);
        defaultConsumer.setInstanceName(this.consumeName + (this.serverPort == null ? "" : this.serverPort));
        defaultConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        switch(this.consumeMode) {
            case ORDERLY:
                if (this.mqSingleListener != null) {
                    defaultConsumer.setMessageListener(new DefaultMqListenerContainer.SingleMessageListenerOrderly());
                } else if (this.mqMultiListener != null) {
                    defaultConsumer.setMessageListener(new DefaultMqListenerContainer.MultiMessageListenerOrderly());
                }
                break;
            case CONCURRENTLY:
                if (this.mqSingleListener != null) {
                    defaultConsumer.setMessageListener(new DefaultMqListenerContainer.SingleMessageListenerConcurrently());
                } else if (this.mqMultiListener != null) {
                    defaultConsumer.setMessageListener(new DefaultMqListenerContainer.MultiMessageListenerConcurrently());
                }
                break;
            default:
                throw new IllegalArgumentException("Property 'consumeMode' was wrong.");
        }

        if (this.mqSingleListener instanceof MqPushConsumerLifecycleListener) {
            ((MqPushConsumerLifecycleListener)this.mqSingleListener).prepareStart(defaultConsumer);
        }

        this.setConsumer(defaultConsumer);
    }

    public String toString() {
        return "DefaultMqListenerContainer [suspendCurrentQueueTimeMillis=" + this.suspendCurrentQueueTimeMillis + ", delayLevelWhenNextConsume=" + this.delayLevelWhenNextConsume + ", consumerGroup=" + this.consumerGroup + ", nameServer=" + this.nameServer + ", topic=" + this.topic + ", consumeMode=" + this.consumeMode + ", selectorExpress=" + this.selectorExpress + ", messageModel=" + this.messageModel + ", consumeThreadMax=" + this.consumeThreadMax + ", charset=" + this.charset + ", started=" + this.started + ", consumer=" + this.consumer + ", msgClassType=" + this.msgClassType + ", msgJavaType=" + this.msgJavaType + ", consumeName=" + this.consumeName + ", batchMaxSize=" + this.batchMaxSize + ", serverPort=" + this.serverPort + "]";
    }

    public void setSuspendCurrentQueueTimeMillis(long suspendCurrentQueueTimeMillis) {
        this.suspendCurrentQueueTimeMillis = suspendCurrentQueueTimeMillis;
    }

    public long getSuspendCurrentQueueTimeMillis() {
        return this.suspendCurrentQueueTimeMillis;
    }

    public void setDelayLevelWhenNextConsume(int delayLevelWhenNextConsume) {
        this.delayLevelWhenNextConsume = delayLevelWhenNextConsume;
    }

    public int getDelayLevelWhenNextConsume() {
        return this.delayLevelWhenNextConsume;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getConsumerGroup() {
        return this.consumerGroup;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getNameServer() {
        return this.nameServer;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setConsumeMode(ConsumeMode consumeMode) {
        this.consumeMode = consumeMode;
    }

    public ConsumeMode getConsumeMode() {
        return this.consumeMode;
    }

    public void setSelectorExpress(String selectorExpress) {
        this.selectorExpress = selectorExpress;
    }

    public String getSelectorExpress() {
        return this.selectorExpress;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }

    public MessageModel getMessageModel() {
        return this.messageModel;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public int getConsumeThreadMax() {
        return this.consumeThreadMax;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setMqSingleListener(MqSingleListener mqSingleListener) {
        this.mqSingleListener = mqSingleListener;
    }

    public void setMqMultiListener(MqMultiListener mqMultiListener) {
        this.mqMultiListener = mqMultiListener;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }

    public void setBatchMaxSize(Integer batchMaxSize) {
        this.batchMaxSize = batchMaxSize;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public class MultiMessageListenerOrderly implements MessageListenerOrderly {
        public MultiMessageListenerOrderly() {
        }

        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            List list = new ArrayList(msgs.size());
            msgs.forEach((messageExt) -> {
                Object message = DefaultMqListenerContainer.this.doConvertMessage(messageExt);
                if (message != null) {
                    list.add(message);
                }

            });

            try {
                long now = System.currentTimeMillis();
                DefaultMqListenerContainer.this.mqMultiListener.onMessage(list);
                long costTime = System.currentTimeMillis() - now;
                DefaultMqListenerContainer.log.debug("consume {} cost: {} ms", list, costTime);
            } catch (Exception var8) {
                DefaultMqListenerContainer.log.warn("consume message failed. messageExt:{}", list, var8);
                context.setSuspendCurrentQueueTimeMillis(DefaultMqListenerContainer.this.suspendCurrentQueueTimeMillis);
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    public class MultiMessageListenerConcurrently implements MessageListenerConcurrently {
        public MultiMessageListenerConcurrently() {
        }

        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            List list = new ArrayList(msgs.size());
            msgs.forEach((messageExt) -> {
                Object message = DefaultMqListenerContainer.this.doConvertMessage(messageExt);
                if (message != null) {
                    list.add(message);
                }

            });

            try {
                long now = System.currentTimeMillis();
                DefaultMqListenerContainer.this.mqMultiListener.onMessage(list);
                long costTime = System.currentTimeMillis() - now;
                DefaultMqListenerContainer.log.debug("consume {} cost: {} ms", list, costTime);
            } catch (Exception var8) {
                DefaultMqListenerContainer.log.warn("consume message failed. messageExt:{}", list, var8);
                context.setDelayLevelWhenNextConsume(DefaultMqListenerContainer.this.delayLevelWhenNextConsume);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    public class SingleMessageListenerOrderly implements MessageListenerOrderly {
        public SingleMessageListenerOrderly() {
        }

        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
            Iterator var3 = msgs.iterator();

            while(var3.hasNext()) {
                MessageExt messageExt = (MessageExt)var3.next();

                try {
                    long now = System.currentTimeMillis();
                    Object message = DefaultMqListenerContainer.this.doConvertMessage(messageExt);
                    if (message != null) {
                        DefaultMqListenerContainer.this.mqSingleListener.onMessage(message);
                        long costTime = System.currentTimeMillis() - now;
                        DefaultMqListenerContainer.log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                    }
                } catch (Exception var10) {
                    DefaultMqListenerContainer.log.warn("consume message failed. messageExt:{}", messageExt, var10);
                    context.setSuspendCurrentQueueTimeMillis(DefaultMqListenerContainer.this.suspendCurrentQueueTimeMillis);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
            }

            return ConsumeOrderlyStatus.SUCCESS;
        }
    }

    public class SingleMessageListenerConcurrently implements MessageListenerConcurrently {
        public SingleMessageListenerConcurrently() {
        }

        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            Iterator var3 = msgs.iterator();

            while(var3.hasNext()) {
                MessageExt messageExt = (MessageExt)var3.next();

                try {
                    long now = System.currentTimeMillis();
                    Object message = DefaultMqListenerContainer.this.doConvertMessage(messageExt);
                    if (message != null) {
                        DefaultMqListenerContainer.this.mqSingleListener.onMessage(message);
                        long costTime = System.currentTimeMillis() - now;
                        DefaultMqListenerContainer.log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
                    }
                } catch (Exception var10) {
                    DefaultMqListenerContainer.log.warn("consume message failed. messageExt:{}", messageExt, var10);
                    context.setDelayLevelWhenNextConsume(DefaultMqListenerContainer.this.delayLevelWhenNextConsume);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
