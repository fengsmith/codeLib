OS X 下面，其实可以在 /etc/resolver 下面创建文件（需要 root 权限）

文件名为域名，如 google.com，内容为 resolv.conf 格式（命令行下 man 5 resolver 可以看详情），如：

nameserver 223.5.5.5

这样一来系统就会使用 223.5.5.5 来解析 google.com 及其子域了。

https://www.v2ex.com/t/123217