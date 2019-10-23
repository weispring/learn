//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.mq.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxc.learn.common.util.web.StringUtil;
import com.lxc.learn.mq.core.DefaultMqListenerContainer;
import com.lxc.learn.mq.core.MqAgent;
import com.lxc.learn.mq.core.MqMultiListener;
import com.lxc.learn.mq.core.MqSingleListener;
import com.lxc.learn.mq.annotation.VPMqListener;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Resource;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.impl.MQClientAPIImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.StandardEnvironment;

@Configuration
@ConditionalOnProperty(
        prefix = "mq",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnClass({MQClientAPIImpl.class})
@Order
public class MqAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MqAutoConfiguration.class);
    @Autowired
    private MqProperties mqProperties;

    public MqAutoConfiguration() {
    }
    

    @Bean
    @ConditionalOnClass({DefaultMQProducer.class})
    @ConditionalOnMissingBean({DefaultMQProducer.class})
    public DefaultMQProducer producer() {
        String groupName = this.mqProperties.getGroupName();
        DefaultMQProducer producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(this.mqProperties.getNameSrvAddr());
        producer.setSendMsgTimeout(this.mqProperties.getProducer().getSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(this.mqProperties.getProducer().getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(this.mqProperties.getProducer().getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(this.mqProperties.getProducer().getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(this.mqProperties.getProducer().getCompressMsgBodyOverHowmuch());
        producer.setRetryAnotherBrokerWhenNotStoreOK(this.mqProperties.getProducer().isRetryAnotherBrokerWhenNotStoreOk());
        producer.setVipChannelEnabled(false);
        return producer;
    }

    @Bean
    @ConditionalOnClass({ObjectMapper.class})
    @ConditionalOnMissingBean(
            name = {"rocketMQMessageObjectMapper"}
    )
    public ObjectMapper rocketMQMessageObjectMapper() {
        return new ObjectMapper();
    }

    @Bean(
            destroyMethod = "destroy"
    )
    @ConditionalOnBean({DefaultMQProducer.class})
    @ConditionalOnMissingBean(
            name = {"mqAgent"}
    )
    public MqAgent mqAgent(DefaultMQProducer producer, @Autowired(required = false) @Qualifier("rocketMQMessageObjectMapper") ObjectMapper objectMapper) {
        MqAgent mqAgent = new MqAgent();
        mqAgent.setProducer(producer);
        if (Objects.nonNull(objectMapper)) {
            mqAgent.setObjectMapper(objectMapper);
        }

        return mqAgent;
    }

    @Configuration
    @ConditionalOnClass({DefaultMQPushConsumer.class})
    @Order
    public static class ListenerContainerConfiguration implements ApplicationContextAware, InitializingBean {
        private ConfigurableApplicationContext applicationContext;
        private AtomicLong counter = new AtomicLong(0L);
        @Resource
        private StandardEnvironment environment;
        @Value("${server.port}")
        private String serverPort;
        @Autowired
        private MqProperties mqProperties;
        private ObjectMapper objectMapper;

        public ListenerContainerConfiguration() {
        }

        @Autowired(
                required = false
        )
        public ListenerContainerConfiguration(@Qualifier("rocketMQMessageObjectMapper") ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = (ConfigurableApplicationContext)applicationContext;
        }

        public void afterPropertiesSet() {
            Map<String, Object> beans = this.applicationContext.getBeansWithAnnotation(VPMqListener.class);
            if (Objects.nonNull(beans)) {
                beans.forEach(this::registerContainer);
            }

        }

        private void registerContainer(String beanName, Object bean) {
            Class<?> clazz = AopUtils.getTargetClass(bean);
            if (!MqSingleListener.class.isAssignableFrom(bean.getClass()) && !MqMultiListener.class.isAssignableFrom(bean.getClass())) {
                throw new IllegalStateException(clazz + " is not instance of " + MqSingleListener.class.getName() + " or " + MqMultiListener.class.getName());
            } else {
                VPMqListener annotation = (VPMqListener)clazz.getAnnotation(VPMqListener.class);
                BeanDefinitionBuilder beanBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultMqListenerContainer.class);
                beanBuilder.addPropertyValue("nameServer", this.mqProperties.getNameSrvAddr());
                beanBuilder.addPropertyValue("topic", this.environment.resolvePlaceholders(annotation.topic()));
                beanBuilder.addPropertyValue("serverPort", this.serverPort);
                String gName = this.environment.resolvePlaceholders(annotation.consumerGroup());
                if (StringUtil.isEmpty(gName)) {
                    gName = this.mqProperties.getGroupName();
                }

                beanBuilder.addPropertyValue("consumerGroup", gName);
                beanBuilder.addPropertyValue("consumeMode", annotation.consumeMode());
                beanBuilder.addPropertyValue("consumeThreadMax", annotation.consumeThreadMax());
                beanBuilder.addPropertyValue("messageModel", annotation.messageModel());
                beanBuilder.addPropertyValue("batchMaxSize", annotation.batchMaxSize());
                beanBuilder.addPropertyValue("selectorExpress", this.environment.resolvePlaceholders(annotation.selectorExpress()));
                if (bean instanceof MqSingleListener) {
                    beanBuilder.addPropertyValue("mqSingleListener", bean);
                } else if (bean instanceof MqMultiListener) {
                    beanBuilder.addPropertyValue("mqMultiListener", bean);
                }

                beanBuilder.addPropertyValue("consumeName", clazz.getName());
                if (Objects.nonNull(this.objectMapper)) {
                    beanBuilder.addPropertyValue("objectMapper", this.objectMapper);
                }

                beanBuilder.setDestroyMethodName("destroy");
                String containerBeanName = String.format("%s_%s", DefaultMqListenerContainer.class.getName(), this.counter.incrementAndGet());
                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)this.applicationContext.getBeanFactory();
                beanFactory.registerBeanDefinition(containerBeanName, beanBuilder.getBeanDefinition());
                DefaultMqListenerContainer container = (DefaultMqListenerContainer)beanFactory.getBean(containerBeanName, DefaultMqListenerContainer.class);
                if (!container.isStarted()) {
                    try {
                        container.start();
                    } catch (Exception var11) {
                        MqAutoConfiguration.log.error("started container failed. {}", container, var11);
                        throw new RuntimeException(var11);
                    }
                }

                MqAutoConfiguration.log.info("register rocketMQ listener to container, listenerBeanName:{}, containerBeanName:{}", beanName, containerBeanName);
            }
        }
    }
}
