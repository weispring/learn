
## springcloud 配置中心config
1.基础版的配置中心（不集成 Eureka）
改为Eureka后，客户端和服务配置有些变化

2.结合 Eureka 版的配置中心

3.实现配置的自动刷新http://localhost:3302/actuator/refresh
webhook
需要借助Spring Cloud Bus 的广播功能
简易实现：提供一个接口，通过Eureka刷新所有的服务，然后配置这个接口为webhook

4.变量获取或者刷新的核心方法org.springframework.cloud.config.client.ConfigServicePropertySourceLocator.getRemoteEnvironment

## 5大组件功能
### 1.