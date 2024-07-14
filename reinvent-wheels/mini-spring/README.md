# <img src="assets/spring-framework.png" width="80" height="80"> mini-spring

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/DerekYRC/mini-spring)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Stars](https://img.shields.io/github/stars/DerekYRC/mini-spring)](https://img.shields.io/github/stars/DerekYRC/mini-spring)
[![Forks](https://img.shields.io/github/forks/DerekYRC/mini-spring)](https://img.shields.io/github/forks/DerekYRC/mini-spring)

**[English](./README_en.md) | 简体中文**

**姊妹版：**[**mini-spring-cloud**](https://github.com/DerekYRC/mini-spring-cloud) **(简化版的 spring cloud 框架)**

## 关于

**mini-spring**是简化版的 spring 框架，能帮助你快速熟悉 spring 源码和掌握 spring 的核心原理。抽取了 spring 的核心逻辑，**代码极度简化，保留 spring 的核心功能**，如 IoC 和 AOP、资源加载器、事件监听器、类型转换、容器扩展点、bean 生命周期和作用域、应用上下文等核心功能。

如果本项目能帮助到你，请给个**STAR，谢谢！！！**

## 功能

#### 基础篇

- [IoC](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#基础篇IoC)
  - [实现一个简单的容器](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#最简单的bean容器)
  - [BeanDefinition 和 BeanDefinitionRegistry](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#BeanDefinition和BeanDefinitionRegistry)
  - [Bean 实例化策略 InstantiationStrategy](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#Bean实例化策略InstantiationStrategy)
  - [为 bean 填充属性](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#为bean填充属性)
  - [为 bean 注入 bean](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#为bean注入bean)
  - [资源和资源加载器](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#资源和资源加载器)
  - [在 xml 文件中定义 bean](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#在xml文件中定义bean)
  - [容器扩展机制 BeanFactoryPostProcess 和 BeanPostProcessor](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#BeanFactoryPostProcess和BeanPostProcessor)
  - [应用上下文 ApplicationContext](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#应用上下文ApplicationContext)
  - [bean 的初始化和销毁方法](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#bean的初始化和销毁方法)
  - [Aware 接口](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#Aware接口)
  - [bean 作用域，增加 prototype 的支持](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#bean作用域增加prototype的支持)
  - [FactoryBean](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#FactoryBean)
  - [容器事件和事件监听器](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#容器事件和事件监听器)
- [AOP](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#基础篇AOP)
  - [切点表达式](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#切点表达式)
  - [基于 JDK 的动态代理](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#基于JDK的动态代理)
  - [基于 CGLIB 的动态代理](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#基于CGLIB的动态代理)
  - [AOP 代理工厂 ProxyFactory](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#AOP代理工厂)
  - [几种常用的 Advice: BeforeAdvice/AfterAdvice/AfterReturningAdvice/ThrowsAdvice](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#几种常用的AdviceBeforeAdviceAfterAdviceAfterReturningAdviceThrowsAdvice)
  - [PointcutAdvisor：Pointcut 和 Advice 的组合](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#pointcutadvisorpointcut和advice的组合)
  - [动态代理融入 bean 生命周期](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#动态代理融入bean生命周期)

#### 扩展篇

- [PropertyPlaceholderConfigurer](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#propertyplaceholderconfigurer)
- [包扫描](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#包扫描)
- [@Value 注解](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#value注解)
- [基于注解@Autowired 的依赖注入](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#autowired注解)
- [类型转换（一）](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#类型转换一)
- [类型转换（二）](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#类型转换二)

#### 高级篇

- [解决循环依赖问题（一）：没有代理对象](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#解决循环依赖问题一没有代理对象)
- [解决循环依赖问题（二）：有代理对象](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#解决循环依赖问题二有代理对象)

#### 其他

- [没有为代理 bean 设置属性(discovered and fixed by kerwin89)](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#bug-fix没有为代理bean设置属性discovered-and-fixed-by-kerwin89)
- [支持懒加载和多切面增强(by zqczgl)](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md#支持懒加载和多切面增强by-zqczgl)

## 使用方法

阅读[changelog.md](https://github.com/DerekYRC/mini-spring/blob/main/changelog.md)

## 提问

[**点此提问**](https://github.com/DerekYRC/mini-spring/issues/4)

## 贡献

欢迎 Pull Request

## 关于我

[**点此了解**](https://github.com/DerekYRC)

手机/微信：**15521077528** 邮箱：**15521077528@163.com**

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=DerekYRC/mini-spring&type=Date)](https://star-history.com/#DerekYRC/mini-spring&Date)

## 版权说明

未取得本人书面许可，不得将该项目用于商业用途

## 参考

- [《Spring 源码深度解析》](https://book.douban.com/subject/25866350/)
- [《Spring 源码解析》](http://svip.iocoder.cn/categories/Spring)
- [《精通 Spring 4.x》](https://book.douban.com/subject/26952826/)
- [《tiny-spring》](https://github.com/code4craft/tiny-spring)
