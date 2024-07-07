package com.taotao.cloud.generator.generator.entity;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * common returnT:公共返回封装类
 *
 * @author zhengkai.blog.csdn.net
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReturnT extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ReturnT() {
        put("code", 0);
        put("msg", "success");
    }

    public static ReturnT error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ReturnT error(String msg) {
        return error(500, msg);
    }

    public static ReturnT error(int code, String msg) {
        ReturnT r = new ReturnT();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static ReturnT define(int code, String msg) {
        ReturnT r = new ReturnT();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static ReturnT ok(String msg) {
        ReturnT r = new ReturnT();
        r.put("msg", msg);
        return r;
    }

    public static ReturnT ok(Map<String, Object> map) {
        ReturnT r = new ReturnT();
        r.putAll(map);
        return r;
    }

    public static ReturnT ok() {
        return new ReturnT();
    }

    @Override
    public ReturnT put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
