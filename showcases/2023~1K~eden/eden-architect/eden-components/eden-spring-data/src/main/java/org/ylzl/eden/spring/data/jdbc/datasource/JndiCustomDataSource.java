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
package org.ylzl.eden.spring.data.jdbc.datasource;

import org.ylzl.eden.commons.env.WebServerUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * JNDI 数据源
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
public class JndiCustomDataSource implements CustomDataSource {

	private String datasourceName;

	private int loginTimeout;

	private PrintWriter printWriter;

	public JndiCustomDataSource(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public CustomDataSource getDataSource(String datasourceName) throws NamingException {
		String dataSourceLookupPrefix = WebServerUtils.getLookup();
		if (!datasourceName.startsWith(dataSourceLookupPrefix)) {
			datasourceName = dataSourceLookupPrefix + datasourceName;
		}
		InitialContext initialContext = new InitialContext();
		return (CustomDataSource) initialContext.lookup(datasourceName);
	}

	public Connection getConnection() throws SQLException {
		try {
			return getDataSource(datasourceName).getConnection();
		} catch (NamingException ne) {
			throw new SQLException(datasourceName);
		}
	}

	public Connection getConnection(String username, String password) throws SQLException {
		try {
			return getDataSource(datasourceName).getConnection(username, password);
		} catch (NamingException ne) {
			throw new SQLException(datasourceName);
		}
	}

	public int getLoginTimeout() throws SQLException {
		return loginTimeout;
	}

	public void setLoginTimeout(int loginTimeout) throws SQLException {
		this.loginTimeout = loginTimeout;
	}

	public PrintWriter getLogWriter() throws SQLException {
		if (this.printWriter == null) {
			this.printWriter = new PrintWriter(System.out);
		}
		return this.printWriter;
	}

	public void setLogWriter(PrintWriter printWriter) throws SQLException {
		this.printWriter = printWriter;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}
