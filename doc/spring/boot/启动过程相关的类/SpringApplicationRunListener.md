1. starting() 方法会在 run() 方法的非常早期的时候去执行。
2. environmentPrepared() 会在环境已经准备好，但应用上下文 ApplicationContext 还没有创建的时候去执行。
3. contextPrepared() 方法会在 ApplicationContext 已经创建好，但是 source 还没有加载的时候去执行。
4. contextLoaded 方法会在应用上下文已经被加载，但是还没有刷新的时候去执行。
5. started() 上下文已经刷新了，应用已经启动过了，但 CommandLineRunner 和 ApplicationRunner 还没有被调用的时候。
6. running() 会在 run() 方法结束后，CommandLineRunner 和 ApplicationRunner 已经被调用了，会被立即调用。
7. failed() 在应用运行失败的时候会调用。


启动阶段的顺序:
1. starting()
2. 准备环境。
3. environmentPrepared()。
4. 创建环境。
5. contextPrepared()。
6. 加载 sources 。
7. contextLoaded() 。
8. refreshed.
9. started()。
10. CommandLineRunner 被调用。
11. ApplicationRunner 被调用。
12. running()。