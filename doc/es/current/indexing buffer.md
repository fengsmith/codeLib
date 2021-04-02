[https://www.elastic.co/guide/en/elasticsearch/reference/current/indexing-buffer.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/indexing-buffer.html)

indexing buffer 用于存储最近索引的文档。buffer 满了之后会就要写到硬盘的 segment 。同一个节点上的各个分片的 buffer 是独立的。
