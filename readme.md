# simple cache
bg:
学习redis之后，根据网上的学习资料，学习并模仿实现一个cache轮子

# 实现功能:
## FIFO队列缓存
最简单的算法，只需要维护一个jdk提供的queue

## 过期策略
都是通过维护一个新的map进行过期时间的维护，通过定时任务进行过期key的清理