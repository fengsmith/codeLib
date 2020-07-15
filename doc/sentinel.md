1. sentinel 客户端的入口是在 SentinelAutoConfiguration
2. SentinelBeanPostProcessor
3. 规则更新原理
    1. nacos 通知到 HttpEventTask ，HttpEventTask 会起一个线程来监听 socket ，接收请求。
    接收到请求后，初步解析请求后，会根据 url 中的 path 来确定到底是增删改查哪种操作，然后再找到相应的 Commander 来处理。
    Commander 会再根据规则的类型来把接收到的 json 数据转换为相应的规则实体。再交给相应的 XxxRuleManager 去更新规则。
    2. XxxRuleManager 里有 SentinelProperty ，SentinelProperty 里有旧的值和 listener 。如果新的值和旧的值不一样就去通知 listener ,listener 会更新值。

4. SpiLoader 值得一读。   
5. SentinelAutoConfiguration 如果是立即加载会调用 InitExecutor.doInit(); 进行一些初始化的工作。初始化一个线程在不断地监听 socket 接收请求。 
6. 修改热点参数好像不会给客户端通知。
7. 应用启动的时候，是在什么时候把 sentinel 的通信端口信息发给 sentinel 服务端的？
8. sentinel 客户端给服务端发送心跳请求。
    1. HeartbeatSenderInitFunc 会初始化线程池，由线程池周期性的给服务端发送心跳请求。
    2. 发送心跳请求的时候会把应用名称、ip、应用类型（暂时好像没啥用）、sentinel 客户端的版本、发送心跳请求时的时间戳、端口号。
    注意这儿的端口号不是应用的端口号而是 sentinel 服务端拉取客户端数据的通信端口号。 
    
9. SimpleHttpHeartbeatSender 给服务器端发送心跳的时候是不是始终只给第一台机器发送了? 
10. nacos 中的授权规则到了应用端就为空了，直接按 ip 修改可以到应用端，但是没有设置 origin 导致所有的请求都放过了