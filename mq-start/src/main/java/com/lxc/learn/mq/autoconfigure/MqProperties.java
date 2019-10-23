package com.lxc.learn.mq.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/23 17:35
 */
@Slf4j
@ConditionalOnProperty(
        prefix = "mq",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@ConfigurationProperties(
        prefix = "mq"
)
@Configuration
public class MqProperties {
    private Boolean enabled = false;
    private String groupName = "mq-start";
    private String nameSrvAddr = "localhost:9876";
    MqProperties.Produce producer = new MqProperties.Produce();
    MqProperties.Consume consume = new MqProperties.Consume();

    public MqProperties() {
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getNameSrvAddr() {
        return this.nameSrvAddr;
    }

    public MqProperties.Produce getProducer() {
        return this.producer;
    }

    public MqProperties.Consume getConsume() {
        return this.consume;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setNameSrvAddr(String nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public void setProducer(MqProperties.Produce producer) {
        this.producer = producer;
    }

    public void setConsume(MqProperties.Consume consume) {
        this.consume = consume;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MqProperties)) {
            return false;
        } else {
            MqProperties other = (MqProperties)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$enabled = this.getEnabled();
                    Object other$enabled = other.getEnabled();
                    if (this$enabled == null) {
                        if (other$enabled == null) {
                            break label71;
                        }
                    } else if (this$enabled.equals(other$enabled)) {
                        break label71;
                    }

                    return false;
                }

                Object this$groupName = this.getGroupName();
                Object other$groupName = other.getGroupName();
                if (this$groupName == null) {
                    if (other$groupName != null) {
                        return false;
                    }
                } else if (!this$groupName.equals(other$groupName)) {
                    return false;
                }

                label57: {
                    Object this$nameSrvAddr = this.getNameSrvAddr();
                    Object other$nameSrvAddr = other.getNameSrvAddr();
                    if (this$nameSrvAddr == null) {
                        if (other$nameSrvAddr == null) {
                            break label57;
                        }
                    } else if (this$nameSrvAddr.equals(other$nameSrvAddr)) {
                        break label57;
                    }

                    return false;
                }

                Object this$producer = this.getProducer();
                Object other$producer = other.getProducer();
                if (this$producer == null) {
                    if (other$producer != null) {
                        return false;
                    }
                } else if (!this$producer.equals(other$producer)) {
                    return false;
                }

                Object this$consume = this.getConsume();
                Object other$consume = other.getConsume();
                if (this$consume == null) {
                    if (other$consume == null) {
                        return true;
                    }
                } else if (this$consume.equals(other$consume)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof MqProperties;
    }

    public int hashCode() {
        int result = 1;
        Object $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
        Object $groupName = this.getGroupName();
        result = result * 59 + ($groupName == null ? 43 : $groupName.hashCode());
        Object $nameSrvAddr = this.getNameSrvAddr();
        result = result * 59 + ($nameSrvAddr == null ? 43 : $nameSrvAddr.hashCode());
        Object $producer = this.getProducer();
        result = result * 59 + ($producer == null ? 43 : $producer.hashCode());
        Object $consume = this.getConsume();
        result = result * 59 + ($consume == null ? 43 : $consume.hashCode());
        return result;
    }

    public String toString() {
        return "MqProperties(enabled=" + this.getEnabled() + ", groupName=" + this.getGroupName() + ", nameSrvAddr=" + this.getNameSrvAddr() + ", producer=" + this.getProducer() + ", consume=" + this.getConsume() + ")";
    }

    public static class Produce {
        private int sendMsgTimeout = 3000;
        private int compressMsgBodyOverHowmuch = 4096;
        private int retryTimesWhenSendFailed = 2;
        private int retryTimesWhenSendAsyncFailed = 2;
        private boolean retryAnotherBrokerWhenNotStoreOk = false;
        private int maxMessageSize = 4194304;

        public Produce() {
        }

        public int getSendMsgTimeout() {
            return this.sendMsgTimeout;
        }

        public int getCompressMsgBodyOverHowmuch() {
            return this.compressMsgBodyOverHowmuch;
        }

        public int getRetryTimesWhenSendFailed() {
            return this.retryTimesWhenSendFailed;
        }

        public int getRetryTimesWhenSendAsyncFailed() {
            return this.retryTimesWhenSendAsyncFailed;
        }

        public boolean isRetryAnotherBrokerWhenNotStoreOk() {
            return this.retryAnotherBrokerWhenNotStoreOk;
        }

        public int getMaxMessageSize() {
            return this.maxMessageSize;
        }

        public void setSendMsgTimeout(int sendMsgTimeout) {
            this.sendMsgTimeout = sendMsgTimeout;
        }

        public void setCompressMsgBodyOverHowmuch(int compressMsgBodyOverHowmuch) {
            this.compressMsgBodyOverHowmuch = compressMsgBodyOverHowmuch;
        }

        public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
            this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
        }

        public void setRetryTimesWhenSendAsyncFailed(int retryTimesWhenSendAsyncFailed) {
            this.retryTimesWhenSendAsyncFailed = retryTimesWhenSendAsyncFailed;
        }

        public void setRetryAnotherBrokerWhenNotStoreOk(boolean retryAnotherBrokerWhenNotStoreOk) {
            this.retryAnotherBrokerWhenNotStoreOk = retryAnotherBrokerWhenNotStoreOk;
        }

        public void setMaxMessageSize(int maxMessageSize) {
            this.maxMessageSize = maxMessageSize;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof MqProperties.Produce)) {
                return false;
            } else {
                MqProperties.Produce other = (MqProperties.Produce)o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getSendMsgTimeout() != other.getSendMsgTimeout()) {
                    return false;
                } else if (this.getCompressMsgBodyOverHowmuch() != other.getCompressMsgBodyOverHowmuch()) {
                    return false;
                } else if (this.getRetryTimesWhenSendFailed() != other.getRetryTimesWhenSendFailed()) {
                    return false;
                } else if (this.getRetryTimesWhenSendAsyncFailed() != other.getRetryTimesWhenSendAsyncFailed()) {
                    return false;
                } else if (this.isRetryAnotherBrokerWhenNotStoreOk() != other.isRetryAnotherBrokerWhenNotStoreOk()) {
                    return false;
                } else {
                    return this.getMaxMessageSize() == other.getMaxMessageSize();
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof MqProperties.Produce;
        }

        public int hashCode() {
            int result = 1;
            result = result * 59 + this.getSendMsgTimeout();
            result = result * 59 + this.getCompressMsgBodyOverHowmuch();
            result = result * 59 + this.getRetryTimesWhenSendFailed();
            result = result * 59 + this.getRetryTimesWhenSendAsyncFailed();
            result = result * 59 + (this.isRetryAnotherBrokerWhenNotStoreOk() ? 79 : 97);
            result = result * 59 + this.getMaxMessageSize();
            return result;
        }

        public String toString() {
            return "MqProperties.Produce(sendMsgTimeout=" + this.getSendMsgTimeout() + ", compressMsgBodyOverHowmuch=" + this.getCompressMsgBodyOverHowmuch() + ", retryTimesWhenSendFailed=" + this.getRetryTimesWhenSendFailed() + ", retryTimesWhenSendAsyncFailed=" + this.getRetryTimesWhenSendAsyncFailed() + ", retryAnotherBrokerWhenNotStoreOk=" + this.isRetryAnotherBrokerWhenNotStoreOk() + ", maxMessageSize=" + this.getMaxMessageSize() + ")";
        }
    }

    public static class Consume {
        private int consumeThreadMin = 4;
        private int consumeThreadMax = 30;

        public Consume() {
        }

        public int getConsumeThreadMin() {
            return this.consumeThreadMin;
        }

        public int getConsumeThreadMax() {
            return this.consumeThreadMax;
        }

        public void setConsumeThreadMin(int consumeThreadMin) {
            this.consumeThreadMin = consumeThreadMin;
        }

        public void setConsumeThreadMax(int consumeThreadMax) {
            this.consumeThreadMax = consumeThreadMax;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof MqProperties.Consume)) {
                return false;
            } else {
                MqProperties.Consume other = (MqProperties.Consume)o;
                if (!other.canEqual(this)) {
                    return false;
                } else if (this.getConsumeThreadMin() != other.getConsumeThreadMin()) {
                    return false;
                } else {
                    return this.getConsumeThreadMax() == other.getConsumeThreadMax();
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof MqProperties.Consume;
        }

        public int hashCode() {
            int result = 1;
            result = result * 59 + this.getConsumeThreadMin();
            result = result * 59 + this.getConsumeThreadMax();
            return result;
        }

        public String toString() {
            return "MqProperties.Consume(consumeThreadMin=" + this.getConsumeThreadMin() + ", consumeThreadMax=" + this.getConsumeThreadMax() + ")";
        }
    }
}
