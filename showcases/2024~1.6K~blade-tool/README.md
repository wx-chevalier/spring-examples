 <p align="center">
   <img src="https://img.shields.io/badge/license-LGPL%20v3-blue.svg" alt="Build Status">
   <img src="https://img.shields.io/badge/JDK-17+-green.svg" alt="Build Status">
   <img src="https://img.shields.io/badge/Spring%20Cloud-2023-blue.svg" alt="Coverage Status">
   <img src="https://img.shields.io/badge/Spring%20Boot-3.2-blue.svg" alt="Downloads">
 </p>  

## SpringBlade微服务开发平台
* 采用前后端分离的模式，前端开源两个框架：[Sword](https://gitee.com/smallc/Sword) (基于 React、Ant Design)、[Saber](https://gitee.com/smallc/Saber) (基于 Vue、Element-UI)
* 后端采用SpringCloud全家桶，并同时对其基础组件做了高度的封装，单独开源出一个框架：[BladeTool](https://github.com/chillzhuang/blade-tool)
* [BladeTool](https://github.com/chillzhuang/blade-tool)已推送至Maven中央库，直接引入即可，减少了工程的臃肿，也可更注重于业务开发
* 集成Sentinel从流量控制、熔断降级、系统负载等多个维度保护服务的稳定性。
* 注册中心、配置中心选型Nacos，为工程瘦身的同时加强各模块之间的联动。
* 使用Traefik进行反向代理，监听后台变化自动化应用新的配置文件。
* 极简封装了多租户底层，用更少的代码换来拓展性更强的SaaS多租户系统。
* 借鉴OAuth2，实现了多终端认证系统，可控制子系统的token权限互相隔离。
* 借鉴Security，封装了Secure模块，采用JWT做Token认证，可拓展集成Redis等细颗粒度控制方案。
* 稳定生产了一年，经历了从 Camden -> Hoxton -> 2021 的技术架构，也经历了从fat jar -> docker -> k8s + jenkins的部署架构
* 项目分包明确，规范微服务的开发模式，使包与包之间的分工清晰。

## 架构图
<img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-framework.png"/>

## 工程结构
``` 
blade-tool
├── blade-core-boot -- 业务包综合模块
├── blade-core-cloud -- cloud封装模块
├── blade-core-datascope -- 数据权限封装模块
├── blade-core-develop -- 代码生成封装模块
├── blade-core-launch -- 基础启动模块
├── blade-core-loadbalancer -- 灰度服务封装模块
├── blade-core-log -- 日志封装模块 
├── blade-core-mybatis -- mybatis拓展封装模块 
├── blade-core-oss -- 对象存储封装模块 
├── blade-core-report -- 报表封装模块 
├── blade-core-secure -- 安全封装模块 
├── blade-core-social -- 第三方登录封装模块 
├── blade-core-swagger -- swagger拓展封装模块 
├── blade-core-test -- 单元测试封装模块 
├── blade-core-tool -- 单元测试封装模块 
└── blade-core-transaction -- 分布式事物封装模块 
```

## 官方信息
* 官网地址：[https://bladex.vip](https://bladex.vip)
* 问答社区：[https://sns.bladex.vip](https://sns.bladex.vip)
* 会员计划：[SpringBlade会员计划](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade会员计划)
* 交流一群：`477853168`(满)
* 交流二群：`751253339`(满)
* 交流三群：`784729540`(满)
* 交流四群：`1034621754`(满)
* 交流五群：`946350912`(满)
* 交流六群：`511624269`(满)
* 交流七群：`298061704`

## 在线演示
* Saber-基于Vue：[https://saber.bladex.vip](https://saber.bladex.vip)
* Sword-基于React：[https://sword.bladex.vip](https://sword.bladex.vip)

## 数据大屏
* 数据大屏展示系统：[https://data.bladex.vip](https://data.bladex.vip)

## 技术文档
* [SpringBlade开发手册一览](https://gitee.com/smallc/SpringBlade/wikis/SpringBlade开发手册)
* [SpringBlade常见问题集锦](https://sns.bladex.vip/article-14966.html)
* [SpringBlade基于Kuboard部署K8S](https://kuboard.cn/learning/k8s-practice/spring-blade/)

## 项目地址
* 核心框架项目地址：[https://gitee.com/smallc/blade-tool](https://gitee.com/smallc/blade-tool)
* 后端Gitee地址：[https://gitee.com/smallc/SpringBlade](https://gitee.com/smallc/SpringBlade)
* 后端Github地址：[https://github.com/chillzhuang/SpringBlade](https://github.com/chillzhuang/SpringBlade)
* 后端SpringBoot版：[https://gitee.com/smallc/SpringBlade/tree/boot/](https://gitee.com/smallc/SpringBlade/tree/boot/)
* 前端框架Sword(基于React)：[https://gitee.com/smallc/Sword](https://gitee.com/smallc/Sword)
* 前端框架Saber(基于Vue2)：[https://gitee.com/smallc/Saber](https://gitee.com/smallc/Saber)
* 前端框架Saber3(基于Vue3)：[https://gitee.com/smallc/Saber3](https://gitee.com/smallc/Saber/tree/3.x/)

## 开源协议
LGPL（[GNU Lesser General Public License](http://www.gnu.org/licenses/lgpl.html)）

LGPL是GPL的一个为主要为类库使用设计的开源协议。和GPL要求任何使用/修改/衍生之GPL类库的的软件必须采用GPL协议不同。LGPL允许商业软件通过类库引用(link)方式使用LGPL类库而不需要开源商业软件的代码。这使得采用LGPL协议的开源代码可以被商业软件作为类库引用并发布和销售。

但是如果修改LGPL协议的代码或者衍生，则所有修改的代码，涉及修改部分的额外代码和衍生的代码都必须采用LGPL协议。因此LGPL协议的开源代码很适合作为第三方类库被商业软件引用，但不适合希望以LGPL协议代码为基础，通过修改和衍生的方式做二次开发的商业软件采用。

## 用户权益
* 允许以引入不改源码的形式免费用于学习、毕设、公司项目、私活等。
* 特殊情况修改代码，但仍然想闭源需经过作者同意。
* 对未经过授权和不遵循 LGPL 协议二次开源或者商业化我们将追究到底。
* 参考请注明：参考自 mica：https://github.com/lets-mica/mica ，blade-tool：https://github.com/chillzhuang/blade-tool 。另请遵循 LGPL 协议。
* `注意`：若禁止条款被发现有权追讨 **19999** 的授权费。

# 界面

## [BladeX](https://bladex.vip/#/vip) 工作流一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow3.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow4.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow5.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/bladex-flow6.png"/></td>
    </tr>
</table>

## [Sword](https://gitee.com/smallc/Sword) 界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-main.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu-edit.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-menu-icon.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-role.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-user.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-dict.png "/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-log.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-locale-cn.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/sword-locale-us.png"/></td>
    </tr>
</table>

## [Saber](https://gitee.com/smallc/Saber) 界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-user.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-role.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-dict.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-dict-select.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-log.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/saber-code.png"/></td>
    </tr>
</table>

## 监控界面一览
<table>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-k8s1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-k8s2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-grafana.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-harbor.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-traefik.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-traefik-health.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-nacos.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-sentinel.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-admin1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-admin2.png"/></td>
    </tr>
    <tr>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-swagger1.png"/></td>
        <td><img src="https://gitee.com/smallc/SpringBlade/raw/master/pic/springblade-swagger2.png"/></td>
    </tr>
</table>

## 鸣谢
* mica（[Mica](https://github.com/lets-mica/mica)）
* 如梦技术（[DreamLu](https://www.dreamlu.net/)）
* avue（[avue](https://avuejs.com/)）
