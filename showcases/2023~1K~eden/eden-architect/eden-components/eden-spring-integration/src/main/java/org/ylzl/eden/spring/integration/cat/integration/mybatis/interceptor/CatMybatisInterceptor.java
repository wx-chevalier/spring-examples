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

package org.ylzl.eden.spring.integration.cat.integration.mybatis.interceptor;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.ylzl.eden.spring.data.mybatis.util.MybatisUtils;

/**
 * Mybatis 集成 CAT 插件
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Intercepts({
	@Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	@Signature(method = "query", type = Executor.class, args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})
})
public class CatMybatisInterceptor implements Interceptor {

	public static final String TYPE_SQL = "SQL";

	public static final String TYPE_SQL_DATABASE = "SQL.database";

	public static final String TYPE_SQL_METHOD = "SQL.method";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String methodName = MybatisUtils.getMethodName(mappedStatement);
		Transaction transaction = Cat.newTransaction(TYPE_SQL, methodName);

		String dataSourceUrl = MybatisUtils.getDatabaseUrl(mappedStatement);
		Cat.logEvent(TYPE_SQL_DATABASE, dataSourceUrl);

		String sql = MybatisUtils.getSql(mappedStatement, invocation);
		Cat.logEvent(TYPE_SQL_METHOD, mappedStatement.getSqlCommandType().name(), Message.SUCCESS, sql);
		try {
			Object returnValue = invocation.proceed();
			transaction.setStatus(Transaction.SUCCESS);
			return returnValue;
		} catch (Throwable e) {
			transaction.setStatus(e);
			Cat.logError(e);
			throw e;
		} finally {
			transaction.complete();
		}
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}
}
