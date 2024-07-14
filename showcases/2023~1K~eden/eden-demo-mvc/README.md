<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/icon.png" align="right" />

[license-apache2.0]:https://www.apache.org/licenses/LICENSE-2.0.html
[github-action]:https://github.com/shiyindaxiaojie/eden-demo-mvc/actions
[sonarcloud-dashboard]:https://sonarcloud.io/dashboard?id=shiyindaxiaojie_eden-demo-mvc

# 分层架构

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/language-java-blue.svg) [![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/readme/license-apache2.0-red.svg)][license-apache2.0] [![](https://github.com/shiyindaxiaojie/eden-demo-mvc/workflows/build/badge.svg)][github-action] [![](https://sonarcloud.io/api/project_badges/measure?project=shiyindaxiaojie_eden-demo-mvc&metric=alert_status)][sonarcloud-dashboard]

本项目使用 MVC 架构构建，MVC 架构是一种面向数据模型的传统架构风格，默认上层依赖于下层，例如控制层依赖业务层、业务层又依赖数据模型层，在垂直业务领域能够满足单一职责原则，通过 Maven 多模块化的开发模式，可以帮助降低复杂应用场景的系统熵值，提升系统开发和运维效率。

> 参考文档请查看 [WIKI](https://github.com/shiyindaxiaojie/eden-demo-mvc/wiki) 。

## 组件构成

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/eden-demo-mvc/component.png)

* **eden-demo-mvc-dao**：数据持久层，与底层 MySQL 进行数据交互。
* **eden-demo-mvc-service**：业务逻辑服务层
* **eden-demo-mvc-web**：请求控制层，对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等
* **eden-demo-mvc-start**：程序启动入口

## 运行流程

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/eden-images/eden-demo-mvc/sequence.png)

## 如何构建

* master 分支对应的是 `eden-demo-mvc 2.4.x`，最低支持 JDK 1.8。
* 1.5.x 分支对应的是 `eden-demo-mvc 1.5.x`，最低支持 JDK 1.8。
* 2.4.x 分支对应的是 `eden-demo-mvc 2.4.x`，最低支持 JDK 1.8。

分层架构使用 Maven 来构建，最快的使用方式是将本项目 `git clone` 到本地，然后执行以下命令：

```bash
./mvnw install
```

执行完毕后，项目将被安装到本地 Maven 仓库。

## 如何使用

### 微调默认配置

> 假定您使用的运行环境为 dev。

* 修改数据库的配置，本项目默认使用 H2 内存数据库启动，基于 Liquibase 在项目启动时自动初始化 SQL 脚本。如果您使用的是外部的
  MySQL 数据库，可以从此处调整下数据库的连接信息：[application-dev.yml](https://github.com/shiyindaxiaojie/eden-demo-mvc/blob/main/eden-demo-mvc-start/src/main/resources/config/application-dev.yml)
* 本项目罗列了 `Redis` 缓存、`RocketMQ` 消息队列、`Dynamic Source` 动态数据源、`ShardingSphere`
  分库分表等常用组件的使用方案，默认通过注释的方式关闭相关代码。您可以根据实际情况去掉相关注释，直接完成组件的集成。

### 运行您的应用

- 在 `项目` 目录下运行 `mvn install`（如果不想运行测试，可以加上 `-DskipTests` 参数）。
- 进入 `eden-demo-cola-start` 目录，执行 `mvn spring-boot:run` 或者启动 `Application`
  类。运行成功的话，可以看到 `Spring Boot` 启动成功的界面。
- 生成的应用中，已经实现了一个简单的 `Rest` 请求，可以在浏览器中输入 http://localhost:8080/api/users/1 进行测试。

> 请注意，我们已经把常用的依赖纳入 eden-dependencies 管理，不建议带版本号覆盖原有的依赖。

## 版本规范

项目的版本号格式为 x.x.x 的形式，其中 x 的数值类型为数字，从 0 开始取值，且不限于 0~9 这个范围。项目处于孵化器阶段时，第一位版本号固定使用 0，即版本号为 0.x.x 的格式。

由于 `Spring Boot 1.5.x` 和 `Spring Boot 2.4.x` 在架构层面有很大的变更，因此我们采取跟 Spring Boot 版本号一致的版本:

* 1.5.x 版本适用于 `Spring Boot 1.5.x`
* 2.4.x 版本适用于 `Spring Boot 2.4.x`
