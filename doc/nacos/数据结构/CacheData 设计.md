1. 维护了缓存的内容。
2. 缓存内容的 md5 。
3. 缓存的 key（tenantId、groupId、dataId）。
4. 订阅缓存的 listeners ，当缓存发生了变化的时候，会通知订阅的 listeners 。
    1. listener 会和一个 md5 关联，表示上次订阅的内容是啥，如果 md5 变更了之后需要通知，不做无效的通知。
    

