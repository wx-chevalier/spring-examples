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

package org.ylzl.eden.spring.data.flyway.datasource;

import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Flyway SQLite 数据源
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class FlywaySQLiteDataSource extends SQLiteDataSource {

	private final DataSource dataSource;
	private Connection connection;

	public FlywaySQLiteDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnectionWithKeepAlive();
	}

	private Connection getConnectionWithKeepAlive() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection = dataSource.getConnection();
		}
		return connection;
	}
}
