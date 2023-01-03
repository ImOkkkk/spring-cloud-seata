## 分布式事务解决方案 [seata](https://github.com/seata/seata) 示例项目

### 技术选型

| 选项           | 组件                 |
| -------------- | -------------------- |
| 服务注册与发现 | Nacos                |
| 服务熔断限流   | Sentinel             |
| 服务通信调用   | Open Feign           |
| 配置中心       | Nacos                |
| 服务网关       | Spring Cloud Gateway |
| 分布式事务     | Seata                |
| 消息队列       | RocketMQ             |

### 项目结构

- common：通用模块
- gateway：服务网关
- order：订单服务
- user：用户服务

......