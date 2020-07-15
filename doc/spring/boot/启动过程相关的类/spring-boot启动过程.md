1. 启动类是 SpringApplication 。
2. 先创建 SpringApplication 。在创建的过程中会加载出 spring factories 。然后找出所有 context 初始化类、listener 类，排序后放到集合中。
3. 然后再 run springApplication 。