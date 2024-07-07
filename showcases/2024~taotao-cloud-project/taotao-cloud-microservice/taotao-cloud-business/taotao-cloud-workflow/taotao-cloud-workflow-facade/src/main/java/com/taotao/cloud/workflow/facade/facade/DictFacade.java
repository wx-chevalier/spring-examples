package com.taotao.cloud.workflow.facade.facade;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.idempotent.annotation.Idempotent;
import com.taotao.cloud.ratelimit.annotation.GuavaLimit;
import com.taotao.cloud.ratelimit.annotation.Limit;
import com.taotao.cloud.security.springsecurity.annotation.NotAuth;
import com.taotao.cloud.sys.api.feign.IFeignDictApi;
import com.taotao.cloud.sys.api.feign.response.FeignDictResponse;
import com.taotao.cloud.web.request.annotation.RequestLogger;
import com.yomahub.tlog.core.annotation.TLogAspect;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 为远程客户端提供粗粒度的调用接口
 */
@Validated
@RestController
@RequestMapping("/sys/feign/dict")
public class DictFacade  implements IFeignDictApi {

	@Override
	@NotAuth
	@Idempotent(perFix = "findByCode")
	@Limit(key = "limitTest", period = 10, count = 3)
	@SentinelResource("findByCode")
	public FeignDictResponse findByCode(@RequestParam(value = "code") String code) {
//		if ("sd".equals(code)) {
//			throw new BusinessException("我出错了");
//			// try {
//			//	Thread.sleep(100000000000L);
//			// } catch (InterruptedException e) {
//			//	throw new RuntimeException(e);
//			// }
//		}
//		Dict dict = service().findByCode(code);
//		return DictConvert.INSTANCE.convert(dict);
		return null;
	}

	@Override
	@Operation(summary = "test", description = "test")
	@RequestLogger
	@NotAuth
	@Idempotent(perFix = "test")
	@TLogAspect(
		value = {"code"},
		pattern = "{{}}",
		joint = ",",
		str = "nihao")
	@Limit(key = "limitTest", period = 10, count = 3)
	@GuavaLimit
	@SentinelResource("test")
	@GetMapping("/test")
	public FeignDictResponse test(@RequestParam(value = "id") String id) {
		LogUtils.info("sldfkslfdjalsdfkjalsfdjl");
//		Dict dict = service().findByCode(id);
//
//		Future<Dict> asyncByCode = service().findAsyncByCode(id);
//
//		Dict dict1;
//		try {
//			dict1 = asyncByCode.get();
//		} catch (InterruptedException | ExecutionException e) {
//			throw new RuntimeException(e);
//		}
//
//		LogUtils.info("我在等待你");

		return null;
		// return IDictMapStruct.INSTANCE.dictToFeignDictRes(dict);
	}

}
