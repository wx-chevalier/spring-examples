package com.taotao.cloud.stock.biz.interfaces;

/**
 * DDD: interfaces 用户界面层（或表示层）  最顶层
 * 负责向用户显示信息和解释用户命令
 * 请求应用层以获取用户所需要展现的数据(比如获取首页的商品数据)
 * 发送命令给应用层要求其执行某个用户命令(实现某个业务逻辑,比如用户要进行转账)
 * 用户界面层应该包含以下的内容:
 * 数据传输对象(Data Transfer Object): DTO也常被称作值对象，VO,实质上与领域层的VO并不相同
 DTO是数据传输的载体,内部不应该存在任何业务逻辑,通过DTO把内部的领域对象与外界隔离。
 * 装配(Assembler): 实现DTO与领域对象之间的相互转换,数据交换,因此Assembler几乎总是同DTO一起出现。
 * 表面,门面(Facade): Façade的用意在于为远程客户端提供粗粒度的调用接口
 它的主要工作就是将一个用户请求委派给一个或多个Service进行处理,也就是我们常说的Controller。
 */
