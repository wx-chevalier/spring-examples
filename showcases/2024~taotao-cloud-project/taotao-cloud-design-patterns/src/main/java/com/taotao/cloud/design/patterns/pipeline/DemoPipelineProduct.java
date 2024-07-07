package com.taotao.cloud.design.patterns.pipeline;


import com.taotao.cloud.design.patterns.pipeline.demo.DemoReq;
import com.taotao.cloud.design.patterns.pipeline.demo.DemoResp;
import lombok.*;

/**
 * 样例-管道产品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoPipelineProduct implements PipelineProduct<DemoPipelineProduct.DemoSignalEnum> {
	/**
	 * 信号量
	 */
	private DemoSignalEnum signal;

	/**
	 * 产品-入参及回参
	 */
	private DemoProductData productData;

	/**
	 * 异常信息
	 */
	private Exception exception;

	/**
	 * 流程Id
	 */
	private String tradeId;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DemoProductData {
		/**
		 * 待验证入参
		 */
		private DemoReq userRequestData;

		/**
		 * 待验证回参
		 */
		private DemoResp userResponseData;
	}

	/**
	 * 产品-信号量
	 *
	 * @author
	 * @date 2023/05/15 13:54
	 */
	@Getter
	public enum DemoSignalEnum {
		/**
		 *
		 */
		NORMAL(0, "正常"),
		/**
		 *
		 */
		CHECK_NOT_PASS(1, "校验不通过"),
		/**
		 *
		 */
		BUSINESS_ERROR(2, "业务异常"),
		/**
		 *
		 */
		LOCK_ERROR(3, "锁处理异常"),
		/**
		 *
		 */
		DB_ERROR(4, "事务处理异常"),

		;
		/**
		 * 枚举码值
		 */
		private final int code;
		/**
		 * 枚举描述
		 */
		private final String desc;

		/**
		 * 构造器
		 *
		 * @param code
		 * @param desc
		 */
		DemoSignalEnum(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
