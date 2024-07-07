package com.taotao.cloud.rpc.registry.apiregistry.apiclient;

import com.taotao.cloud.rpc.registry.apiregistry.ApiRegistryProperties;
import com.taotao.cloud.rpc.registry.apiregistry.CoreRequestInterceptor;
import com.taotao.cloud.rpc.registry.apiregistry.RequestInfo;
import com.taotao.cloud.rpc.registry.apiregistry.anno.ApiIgnore;
import com.taotao.cloud.rpc.registry.apiregistry.base.*;
import com.taotao.cloud.rpc.registry.apiregistry.loadbalance.BaseLoadBalance;
import com.taotao.cloud.rpc.registry.apiregistry.rpcclient.IRpcClient;
import com.taotao.cloud.rpc.registry.apiregistry.rpcclient.RpcClientFactory;
import java.lang.reflect.Method;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

public class Aspects {
    @Order(2)
    @Aspect
    public static class ApiClientAspect {
        private ClientAspect clientAspect = new ClientAspect();
        @Pointcut("@within(com.taotao.cloud.rpc.registry.apiregistry.anno.ApiClient)")
        public void pointCut(){};

        @Around("pointCut()")
        public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
            return clientAspect.aop(joinPoint);
        }
    }

    @Order(2)
    @Aspect
    public static class AllClientAspect  {
        private ClientAspect clientAspect = new ClientAspect();
        @Pointcut("@within(com.taotao.cloud.rpc.registry.apiregistry.anno.ApiClient) || @within(org.springframework.cloud.openfeign.FeignClient)")
        public void pointCut(){};

        @Around("pointCut()")
        public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
            return clientAspect.aop(joinPoint);
        }
    }

    public static class ClientAspect {
        public Object aop(ProceedingJoinPoint joinPoint) throws Throwable {
//            if(!ApiRegistryProperties.getApiClientAspectEnabled()||
//                    !ApiRegistryProperties.getEnabled()){
//                return joinPoint.proceed();
//            }
			Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
//			ApiClientInfo apiClientInfo = ApiUtils.getApiClient(method);
//            if(apiClientInfo ==null){
//                throw new ApiRegistryException("未找到ApiClient或FeignClient注解信息");
//            }
//			ApiIgnore apiIgnore=ApiUtils.getApiIgnore(method);
//            if(apiIgnore!=null){
//                return joinPoint.proceed();
//            }
//            //测试模式
//            if(ApiRegistryProperties.getTestEnabled()){
//				Object eurekaResult = joinPoint.proceed();
//                String methodPath=method.getDeclaringClass().getName()+"."+method.getName();
////                if(!StringUtils.hitCondition(ApiRegistryProperties.getTestSkipMethods().toLowerCase(),methodPath.toLowerCase())){
////                    try{
////						Object rpcResult = rpc(apiClientInfo,method,joinPoint);
////                        ApiUtils.checkObjectEqual(eurekaResult,rpcResult);
////                    }catch (Exception e){
////                        String message="appName:"+apiClientInfo.getName()+" method:"+methodPath+" exp:"+e.getMessage();
//////                        LogUtils.warn("apiRegistry 测试对比rpc结果异常",message);
////                    }
////                }
//                return eurekaResult;
//            }

            try {
                return rpc(null,method,joinPoint);
            }
            catch (Exception e){
//                if(ApiRegistryProperties.getWarnEnabled()) {
////					LogUtils.warn("apiRegistry rpc调用异常", e.getMessage());
//                }
                throw e;
            }
        }

        protected Object rpc(ApiClientInfo apiClientInfo, Method method, ProceedingJoinPoint joinPoint){
//            return this.retryExecute(()->{
////				BaseLoadBalance loadBalance = ContextUtils.getBean( BaseLoadBalance.class,false);
////				String hostPort = loadBalance.getAvailableHostPort(apiClientInfo.getName());
////                String url = ApiUtils.getUrl(hostPort,apiClientInfo.getPath());
////                if(StringUtils.isEmpty(url)) {
////					throw new ApiRegistryException("未找到服务host信息");
////				}
//
//                //解析
//				BaseApiClientParser.ApiClientParserInfo requestParserInfo = new BaseApiClientParser.ApiClientParserInfo(apiClientInfo.getName(),url,method,joinPoint);
//				RequestInfo requestInfo = new ApiClientParser().parse(requestParserInfo);
//                //拦截器
////				List<CoreRequestInterceptor> interceptors = ContextUtils.getBeans(CoreRequestInterceptor.class);
////                if(interceptors!=null){
////                    for(CoreRequestInterceptor i:interceptors){
////                        i.append(requestInfo);
////                    }
////                }
//
//                //请求
//				IRpcClient httpClient = RpcClientFactory.create();
//                return httpClient.execute(requestInfo,method.getGenericReturnType());
//            });
			return null;
        }

//        protected Object retryExecute(Callable.Func0 func0){
//            ApiRegistryException exp=null;
//            for(Integer i=0;i<ApiRegistryProperties.getRegistryFailRetryTimes();i++){
//                try {
//                    return func0.invoke();
//                }catch (ApiRegistryHttpStateException exception){
//					BaseLoadBalance loadBalance = ContextUtils.getBean( BaseLoadBalance.class,false);
//                    if(loadBalance!=null) {
//                        loadBalance.addFail(exception.getAppName(),exception.getUrl());
//                    }
//                    exp=exception;
//                }
//            }
//            throw exp;
//        }
    }
}
