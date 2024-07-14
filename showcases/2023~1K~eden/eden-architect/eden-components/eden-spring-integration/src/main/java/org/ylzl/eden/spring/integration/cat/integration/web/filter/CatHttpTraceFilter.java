/*
 * Copyright 2012-2019 the original author or authors.
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

package org.ylzl.eden.spring.integration.cat.integration.web.filter;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.servlet.CatFilter;
import org.slf4j.MDC;
import org.ylzl.eden.spring.integration.cat.tracing.TraceContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Http 链路过滤器
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class CatHttpTraceFilter extends CatFilter {

	public static final String TYPE_HTTP_TRACE = "Http.trace";

	public static final String HTTP_HEADER_CHILD_MESSAGE_ID = "X-CAT-CHILD-MESSAGE-ID";

	public static final String HTTP_HEADER_PARENT_MESSAGE_ID = "X-CAT-PARENT-MESSAGE-ID";

	public static final String HTTP_HEADER_ROOT_MESSAGE_ID = "X-CAT-ROOT-MESSAGE-ID";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		Transaction t = Cat.newTransaction(TYPE_HTTP_TRACE, request.getRequestURI());
		try {
			Cat.Context context = TraceContext.getContext();
			context.addProperty(Cat.Context.ROOT, request.getHeader(HTTP_HEADER_ROOT_MESSAGE_ID));
			context.addProperty(Cat.Context.PARENT, request.getHeader(HTTP_HEADER_PARENT_MESSAGE_ID));
			context.addProperty(Cat.Context.CHILD, request.getHeader(HTTP_HEADER_CHILD_MESSAGE_ID));
			Cat.logEvent(TYPE_HTTP_TRACE, request.getRequestURI());
			Cat.logRemoteCallClient(context, Cat.getManager().getDomain());

			MDC.put(TraceContext.TRACE_ID, context.getProperty(Cat.Context.ROOT));
			filterChain.doFilter(servletRequest, servletResponse);
			t.setStatus(Transaction.SUCCESS);
		} catch (ServletException | IOException e) {
			t.setStatus(e);
			Cat.logError(e);
			throw e;
		} catch (Throwable e) {
			t.setStatus(e);
			Cat.logError(e);
			throw new RuntimeException(e);
		} finally {
			t.complete();
			TraceContext.remove();
		}
	}
}
