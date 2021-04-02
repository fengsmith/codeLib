https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-cluster.html

为了让集群负载均衡，主节点的一个作用就是决定在什么时候迁移节点间的分片，哪些分片分配给哪些节点。

重新负载均衡的时候会基于分配给各个节点上的分片来计算各个节点的负载，然后再在各个节点之间迁移分片，减轻负载重的节点的负载，增加负载轻的节点的负载。