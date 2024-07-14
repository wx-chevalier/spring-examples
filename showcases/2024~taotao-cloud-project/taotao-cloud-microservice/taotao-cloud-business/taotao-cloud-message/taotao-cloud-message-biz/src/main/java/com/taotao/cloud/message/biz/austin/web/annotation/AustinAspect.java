package com.taotao.cloud.message.biz.austin.web.annotation;

import java.lang.annotation.*;

/**
 * @author kl
 * @version 1.0.0
 * @description 接口切面注解
 * @since 2023/2/23 9:01
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AustinAspect {

}