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

package org.ylzl.eden.spring.data.flyway.async;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.flywaydb.core.api.configuration.Configuration;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 异步 Flyway
 *
 * <p>变更日志：Flyway 升级到 6.X
 *
 * <ul>
 *   <li>{@code flyway.getDataSource()} 方法被移除
 *   <li>Flyway 构造函数新增 {@link Configuration} 为必须参数
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@Slf4j
public class AsyncFlyway extends Flyway {

	private static final String STARTING_ASYNC = "Starting Flyway asynchronously";
	private static final String STARTING_SYNC = "Starting Flyway synchronously";
	private static final String EXCEPTION = "Flyway could not start correctly, your database is not ready：{}";
	private static final String STARTED = "Flyway has updated your database in {} ms";
	private static final String SLOWNESS = "Flyway took more than {} seconds to start up!";
	private static final String STOP_WATCH_ID = "flyway";

	private static final long SLOWNESS_THRESHOLD = 5;

	private final boolean aysnc;

	private final AsyncTaskExecutor executor;

	private final Configuration configuration;

	public AsyncFlyway(boolean aysnc, AsyncTaskExecutor executor, Configuration configuration) {
		super(configuration);
		this.aysnc = aysnc;
		this.configuration = configuration;
		this.executor = executor;
	}

	@Override
	public MigrateResult migrate() throws FlywayException {
		if (aysnc) {
			try (Connection ignored = configuration.getDataSource().getConnection()) {
				executor.submit(
					() -> {
						try {
							log.debug(STARTING_ASYNC);
							return initDb();
						} catch (FlywayException e) {
							log.error(EXCEPTION, e.getMessage(), e);
							throw new FlywayException(e);
						}
					});
			} catch (SQLException e) {
				log.error(EXCEPTION, e.getMessage(), e);
				throw new FlywayException(e);
			}
			return null;
		} else {
			log.debug(STARTING_SYNC);
			return initDb();
		}
	}

	protected MigrateResult initDb() throws FlywayException {
		StopWatch watch = new StopWatch("flyway");
		watch.start();
		MigrateResult migrateResult = super.migrate();
		watch.stop();
		log.debug(STARTED, watch.getTotalTimeMillis());
		if (watch.getTotalTimeMillis() > SLOWNESS_THRESHOLD * 1000L) {
			log.warn(SLOWNESS, SLOWNESS_THRESHOLD);
		}
		return migrateResult;
	}
}
