# simple cache
bg:
学习redis之后，根据网上的学习资料，学习并模仿实现一个cache轮子

# 实现功能:
都可以在 ICache 中查看接口
1. 支持过期时间
2. 支持指定时间过期
3. 支持获取缓存的过期处理类
4. 删除监听类
5. 慢日志监听类
6. 加载信息
7. 持久化
8. 淘汰策略


## FIFO队列缓存
最简单的算法，只需要维护一个jdk提供的queue

## 过期策略
都是通过维护一个新的map进行过期时间的维护，通过定时任务进行过期key的清理

## 使用动态代理
支持fluent风格的调用,更优雅的使用方式

