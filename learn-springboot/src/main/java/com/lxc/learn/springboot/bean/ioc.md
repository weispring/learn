- bean的注册、创建和代理
```
public void refresh() throws BeansException, IllegalStateException {
        Object var1 = this.startupShutdownMonitor;
        synchronized(this.startupShutdownMonitor) {
            this.prepareRefresh();
            ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
            this.prepareBeanFactory(beanFactory);

            try {
                this.postProcessBeanFactory(beanFactory);
                //查找、解析、并注册bean
                this.invokeBeanFactoryPostProcessors(beanFactory);
                this.registerBeanPostProcessors(beanFactory);
                this.initMessageSource();
                this.initApplicationEventMulticaster();
                this.onRefresh();
                this.registerListeners();
                //实例化bean,实例化后可能还会创建对应的代理对象，分为jdk和cglib代理
                //AspectJAwareAdvisorAutoProxyCreator的postProcessAfterInitialization方法再其父类AbstractAutoProxyCreator中实现，其会对已经实例化的bean进行wrap。
                this.finishBeanFactoryInitialization(beanFactory);
                this.finishRefresh();
            } catch (BeansException var9) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var9);
                }

                this.destroyBeans();
                this.cancelRefresh(var9);
                throw var9;
            } finally {
                this.resetCommonCaches();
            }

        }
    }

```

AOP概念
```
方面（Aspect）： 一个关注点的模块化，这个关注点实现可能 另外横切多个对象。事务管理是J2EE应用中一个很好的横切关注点例子。方面用Spring的 Advisor或拦截器实现。
连接点（Joinpoint）: 程序执行过程中明确的点，如方法的调 用或特定的异常被抛出。
通知（Advice）: 在特定的连接点，AOP框架执行的动作。各种类 型的通知包括“around”、“before”和“throws”通知。通知类型将在下面讨论。许多AOP框架 包括Spring都是以拦截器做通知模型，维护一个“围绕”连接点的拦截器 链。
切入点（Pointcut）: 指定一个通知将被引发的一系列连接点 的集合。AOP框架必须允许开发者指定切入点：例如，使用正则表达式。
引入（Introduction）: 添加方法或字段到被通知的类。 Spring允许引入新的接口到任何被通知的对象。例如，你可以使用一个引入使任何对象实现 IsModified接口，来简化缓存。
目标对象（Target Object）: 包含连接点的对象。也被称作 被通知或被代理对象。
AOP代理（AOP Proxy）: AOP框架创建的对象，包含通知。 在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。
织入（Weaving）: 组装方面来创建一个被通知对象。这可以在编译时 完成（例如使用AspectJ编译器），也可以在运行时完成。Spring和其他纯Java AOP框架一样， 在运行时完成织入。
```