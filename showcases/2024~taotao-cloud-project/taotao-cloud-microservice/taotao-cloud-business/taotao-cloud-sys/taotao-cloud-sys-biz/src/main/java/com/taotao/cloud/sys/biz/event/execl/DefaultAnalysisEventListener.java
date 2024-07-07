/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.sys.biz.event.execl; // package com.taotao.cloud.sys.biz.event.execl;
//
// import com.alibaba.excel.context.AnalysisContext;
// import com.hccake.common.excel.kit.Validators;
// import com.hccake.common.excel.vo.ErrorMessage;
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;
//
// import jakarta.validation.ConstraintViolation;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;
//
/// **
// * 默认的 AnalysisEventListener
// *
// * @author lengleng
// * @author L.cm
// * @since 2021/4/16
// */
// @Slf4j
// public class DefaultAnalysisEventListener extends ListAnalysisEventListener<Object> {
//
//	private final List<Object> list = new ArrayList<>();
//
//	private final List<ErrorMessage> errorMessageList = new ArrayList<>();
//
//	@Setter
//	private Long lineNum = 1L;
//
//	@Override
//	public void invoke(Object o, AnalysisContext analysisContext) {
//		lineNum++;
//
//		Set<ConstraintViolation<Object>> violations = Validators.validate(o);
//		if (!violations.isEmpty()) {
//			Set<String> messageSet = violations.stream().map(ConstraintViolation::getMessage)
//					.collect(Collectors.toSet());
//			errorMessageList.add(new ErrorMessage(lineNum, messageSet));
//		}
//		else {
//			list.add(o);
//		}
//	}
//
//	@Override
//	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//		log.debug("Excel read analysed");
//	}
//
//	@Override
//	public List<Object> getList() {
//		return list;
//	}
//
//	@Override
//	public List<ErrorMessage> getErrors() {
//		return errorMessageList;
//	}
//
// }
