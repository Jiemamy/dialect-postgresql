/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.apache.commons.lang.Validate;

import org.jiemamy.JiemamyContext;
import org.jiemamy.dialect.DatabaseMetadataParser;
import org.jiemamy.dialect.DefaultDatabaseMetadataParser;
import org.jiemamy.dialect.DefaultForeignKeyImportVisitor;
import org.jiemamy.dialect.Dialect;
import org.jiemamy.dialect.ForeignKeyImportVisitor;
import org.jiemamy.dialect.ParseMetadataConfig;

/**
 * PostgreSQL用{@link DatabaseMetadataParser}実装クラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class PostgreSqlDatabaseMetadataParser extends DefaultDatabaseMetadataParser {
	
	private final PostgreSqlDbObjectImportVisitor dbObjectImportVisitor;
	
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dialect {@link Dialect}
	 */
	public PostgreSqlDatabaseMetadataParser(PostgreSqlDialect dialect) {
		this(new PostgreSqlDbObjectImportVisitor(dialect), new DefaultForeignKeyImportVisitor(dialect));
	}
	
	// private constructor capture idiom
	private PostgreSqlDatabaseMetadataParser(PostgreSqlDbObjectImportVisitor dbObjectImportVisitor,
			ForeignKeyImportVisitor foreignKeyImportVisitor) {
		super(dbObjectImportVisitor, foreignKeyImportVisitor);
		this.dbObjectImportVisitor = dbObjectImportVisitor;
	}
	
	@Override
	public void parseMetadata(JiemamyContext context, DatabaseMetaData meta, ParseMetadataConfig config)
			throws SQLException {
		Validate.notNull(context);
		Validate.notNull(meta);
		Validate.notNull(config);
		
		dbObjectImportVisitor.setSchema(config.getSchema());
		super.parseMetadata(context, meta, config);
	}
}
