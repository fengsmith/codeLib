1. Filter 和 Interceptor 的区别。
2. Filter 中的 url pattern 中的 /* 和 Interceptor 中的 /* 和 /** 的区别。
3. Filter 
    1. ApplicationFilterFactory 中创建了 ApplicationFilterChain 。
    2. 一个请求到来之后，到底应该走哪些 filter ，首先会进行 path 的匹配，把匹配到的 filter 放到 filterChain 中。
    3. 在 ApplicationFilterFactory 中的第 193 行可以看出 /* 可以匹配所有的 path 。
4. spring boot bean 是怎么注入进去的 

        PostProcessorRegistrationDelegate  95 
    
        AbstractApplicationContext  532  invokeBeanFactoryPostProcessors create beans
        在 AntPathMatcher 196 行打个断点，有惊喜。
5. 在拦截器中设置了 url-pattern 后，一个请求来到之后，怎么来决定这个拦截器要不要拦截的逻辑。


        java.lang.Exception
        	at shfq.controller.TraceUtil.printStackTrace(TraceUtil.java:20)
        	at org.springframework.web.servlet.handler.MappedInterceptor.matches(MappedInterceptor.java:159)
        	at org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandlerExecutionChain(AbstractHandlerMapping.java:480)
        	at org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:414)
        	at org.springframework.web.servlet.DispatcherServlet.getHandler(DispatcherServlet.java:1231)
        	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1014)
        	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)
        	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)
        	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:897)
        	at javax.servlet.http.HttpServlet.service(HttpServlet.java:634)
        	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)
        	at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
        	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92)
        	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)
        	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
        	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
        	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
        	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
        	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:199)
        	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
        	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)
        	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
        	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
        	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
        	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
        	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)
        	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
        	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:834)
        	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1417)
        	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
        	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
        	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
        	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
        	at java.lang.Thread.run(Thread.java:745)
        	
6. 在 sentinel-nacos-demo 中的 SentinelDataSourceHandler 202 行打个断点，再在 NacosDataSource 的构造方法中打个断点，可以知道。。。        	
        
        
        