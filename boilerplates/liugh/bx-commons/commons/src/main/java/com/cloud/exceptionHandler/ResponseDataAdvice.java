package com.cloud.exceptionHandler;

import com.alibaba.fastjson.JSONObject;
import com.cloud.utils.ComUtil;
import com.cloud.utils.ResultHelper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * 统一响应
 * Created by liugh on 2019/6/1.
 */
@ControllerAdvice
@ResponseBody
public class ResponseDataAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
         if(o.toString().endsWith("}")){
             JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(o));
             if(!ComUtil.isEmpty(jsonObject.get("error"))){
                 return ResultHelper.failed(o,"Unauthorized");
             }
         }
        return o;
    }
}
