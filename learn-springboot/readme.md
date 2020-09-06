- 1.常用注解
- 2.endpoint
- 3.main方法

----
@Async

@Cache 待笔记，源码已写

@SpringBootCondition待笔记

@Transtional 待笔记

Endpont 待笔记

Environment 待笔记，源码已写

DispatcherServlet

RequestContextFilter

UniqueBeanNameGenerator

AnnotatedClassFinder handlerMappings DispatcherServlet EndpointWebMvcManagementContextConfiguration

swagger springfox.documentation.swagger2.web.Swagger2Controller

多语言处理 org.springframework.web.filter.RequestContextFilter#doFilterInternal 从Accept-Language 获取变量保存到线程变量。
 
拦截器改变语言 org.springframework.web.servlet.i18n.LocaleChangeInterceptor#preHandle
在这里执行 DispatcherServlet 中的handlerMethod预处理时 mappedHandler.applyPreHandle(processedRequest, response) 
虽然可以通过spring的过滤器RequestContextFilter设置语言线程变量，但是考虑到浏览器兼容问题（会有默认值，并且不同版本浏览器，可能不同），不宜使用头部Accept-Language
前面可能（RequestContextFilter）已经设置值，但是可以通过此拦截器改变，甚好。
String newLocale = request.getParameter(this.getParamName()); paramName可配置。

spring缓存动态扩展实现 参考learn-redis实现