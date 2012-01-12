/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/02/12
 *
 * This file is part of Jiemamy.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jiemamy.dialect.postgresql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jiemamy.dialect.DbObjectImportVisitor;
import org.jiemamy.dialect.DefaultDbObjectImportVisitor;
import org.jiemamy.dialect.Dialect;
import org.jiemamy.model.view.SimpleJmView;

/**
 * PostgreSQL用{@link DbObjectImportVisitor}実装クラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class PostgreSqlDbObjectImportVisitor extends DefaultDbObjectImportVisitor {
	
	private static Logger logger = LoggerFactory.getLogger(PostgreSqlDbObjectImportVisitor.class);
	
	private String schema;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dialect {@link Dialect}
	 */
	public PostgreSqlDbObjectImportVisitor(PostgreSqlDialect dialect) {
		super(dialect);
	}
	
	@Override
	protected SimpleJmView createView(String viewName) throws SQLException {
		Validate.notNull(viewName);
		
		SimpleJmView view = new SimpleJmView();
		view.setName(viewName);
		
		try {
			Connection connection = getMeta().getMetaData().getConnection();
			String definition = getViewDefinition(connection, viewName);
			view.setDefinition(definition);
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			logger.error("exception is thrown", e);
		}
		
		return view;
	}
	
	String getViewDefinition(Connection conn, String viewName) throws SQLException {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM pg_views WHERE schemaname = ? AND viewname = ?;");
			ps.setString(1, schema);
			ps.setString(2, viewName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("definition");
			}
			return null;
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}
	
	void setSchema(String schema) {
		this.schema = schema;
	}
}
