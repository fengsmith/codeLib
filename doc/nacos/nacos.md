1. nacos 服务端更新配置的原理（/v1/cs/configs）。
    1. ConfigController 控制器接收浏览器端的修改请求。
    2. 插入或更新 config_info 表。
    3. 插入 his_config_info 表。
    4. fire event 触发配置更新事件。
    5. 找出事件对应的监听器。
    6. 监听器处理事件 AsyncNotifyService。
        1. 找出 nacos 集群 ip 集合。
        2. 为每个 ip 创建一个任务。
        3. 把这些任务添加到队列中。
        4. 线程池去执行任务队列。
        5. /nacos/v1/cs/communication/dataChange?dataId=spring-boot-sentinel-nacos-demo-sentinel-flow&group=TEST_GROUP&tenant=f1fdc524-2a96-4626-b1e5-36e7a0474a76 
        通知 nacos 服务器配置文件发生了变化。 
    
    7. CommunicationController(notifyConfigInfo方法即/nacos/v1/cs/communication/dataChange)接收配置改变的消息。
        1. 交给 DumpService 复制服务去复制。
        2. 交给 TaskManager 把这个任务添加到任务集合中。
        3. 在 TaskManager 里有个 ProcessRunnable 线程在不停地执行任务。如果任务执行完之后会通知其他线程往任务 tasks 里添加任务。每 10 毫秒就执行一次，先加入的任务执行完后才可以继续往进去加任务。这儿涉及到的 ReentrantLock 和 Condition 可以好好看看。 
            1. 根据 tenantId、groupId、dataId 在数据库里查询配置文件。
            2. 把查询到的配置文件保存到磁盘中（如果是同样的文件就不保存，如果是嵌入式数据库也不需要保存，嵌入式数据库在本地，所以不会因为网络原因而查询失败）。
            3. 更新缓存的 key 和 md5 值。
            4. 文件内容变了之后，还会触发一个 LocalDataChangeEvent 事件。
                1. LongPollingService 这个监听器负责处理本地数据变更事件。
                    1. 长轮询服务保存了哪些客户端订阅（还没有响应过的请求）了它，然后依次迭代所有订阅了的客户端，看哪些客户端对修改了的文件感兴趣。
                    如果客户端对修改了的文件感兴趣的话，则长轮询服务会把订阅关系给删除，并返回客户端响应，把改变了的文件的 key 给客户端返回去。
                
        4. 保存成功之后还会触发一个 log 事件。
        5.
2. 其中客户端的 ClientWorker 也会调用服务器端的 CommunicationController(notifyConfigInfo方法，即)        

2. TaskManager 需要好好看下，里面有些同步的内容没搞懂。           
1. http://localhost:8848/nacos/v1/cs/configs 修改配置文件的处理流程
    1. 数据持久化到数据库中 处理的方法:com.alibaba.nacos.config.server.controller.ConfigController.publishConfig()。
    2. EventDispatcher.fireEvent() 触发 ConfigDataChangeEvent 配置数据变更事件。
    3. AsyncNotifyService.onEvent() 去处理 ConfigDataChangeEvent 事件。
        1. 为每个 nacos 服务封装 NotifySingleTask 任务并加入到队列中。
        2. AsyncTask 加入到 EXECUTOR 线程池执行队列中的任务。
    4. AsyncTask 循环执行 NotifySingleTask 主要是循环通知各个 nacos 服务，告诉大家有配置发生了变化:
        1. 判断当前执行任务的 ip 是不是在服务器列表中。
            1. 是
                1. 检查当前执行任务对应的 ip 是否健康
                    1. 不健康:加入过一段时间再执行这个任务
                    2. 健康:执行 http://172.16.10.165:8848/nacos/v1/cs/communication/dataChange?dataId=sentinel-flow.properties&group=boot-sentinel-nacos&tenant=531a592c-b09a-4e47-ad3a-c00edcb506d1 通知 nacos 服务，配置数据发生了变更。
            2. 否:退出
        2. 检查当前执行任务对应的 ip 是否健康，如果不健康的话，则过一段时间再执行该任务。
    5. com.alibaba.nacos.config.server.controller.CommunicationController.notifyConfigInfo 接收配置数据发生了变更事件:
        1. 把这次通知封装成一个 DumpTask,并加入到任务集合中，TaskManager 单线程每隔 100 毫秒执行 process() 方法。
        2. 找出 DumpTask 任务对应的 DumpProcessor,交给 DumpProcessor 去处理。
        3. DumpProcessor.process():
            1. 判断是否是灰度、tag 发布等。
            2. 从数据库中取出配置信息
                1. 配置信息不为空:
                    1. 集群或者是孤立模式但使用 mysql 就保存配置信息到硬盘，为什么呢？除此之外用的就是孤立模式的 derby ，嵌入式数据库不会出现网络问题，所以不需要把配置信息以文本的方式保存到硬盘
                    2. 如果缓存中配置信息的 md5 为空（首次添加配置）或者缓存发生了变化，需要更新缓存的 md5 值和更新时间的时间戳。
                    3. EventDispatcher.fireEvent 激活 LocalDataChangeEvent 局部数据更新事件，由 LongPollingService 去处理。
                    4. LongPollingService.onEvent() 
                        1. 封装 DataChangeTask ，用调度器去执行这个任务。
                            1. DataChangeTask.run()   
3. NacosConfigService 也需要看.
4. 应用通过调用 /v1/cs/configs/listener 如果数据没有变的话 LongPollingService 添加长轮询客户端，如果数据发生了变化。                           
5. NacosConfigService 值得读，可以在构造方法处打个断点，在启动应用的时候观察一下。