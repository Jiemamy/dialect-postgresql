/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/01/23
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jiemamy.JiemamyContext;
import org.jiemamy.SqlFacet;
import org.jiemamy.composer.exporter.SimpleSqlExportConfig;
import org.jiemamy.model.column.JmColumnBuilder;
import org.jiemamy.model.datatype.RawTypeCategory;
import org.jiemamy.model.datatype.RawTypeDescriptor;
import org.jiemamy.model.datatype.SimpleDataType;
import org.jiemamy.model.datatype.SimpleRawTypeDescriptor;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.model.sql.SqlStatement;
import org.jiemamy.model.table.JmTableBuilder;
import org.jiemamy.model.table.JmTable;

/**
 * {@link PostgreSqlEmitter}のテストクラス。
 * 
 * @version $Id$
 * @author daisuke
 */
public class PostgreSqlEmitterTest {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PostgreSqlEmitterTest.class);
	
	private static final RawTypeDescriptor INTEGER = new SimpleRawTypeDescriptor(RawTypeCategory.INTEGER, "INTEGER",
			"int4");
	
	private static final RawTypeDescriptor VARCHAR = new SimpleRawTypeDescriptor(RawTypeCategory.VARCHAR);
	
	private PostgreSqlEmitter emitter;
	
	private SimpleSqlExportConfig config;
	
	private JiemamyContext context;
	
	
	/**
	 * テストを初期化する。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Before
	public void setUp() throws Exception {
		emitter = new PostgreSqlEmitter(new PostgreSqlDialect());
		
		config = new SimpleSqlExportConfig();
		config.setDataSetIndex(-1);
		config.setEmitCreateSchema(true);
		config.setEmitDropStatements(true);
		
		context = new JiemamyContext(SqlFacet.PROVIDER);
	}
	
	/**
	 * 空のcontextをemitしても文は生成されない。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test01_空のcontextをemitしても文は生成されない() throws Exception {
		List<SqlStatement> statements = emitter.emit(context, config);
		assertThat(statements.size(), is(0));
	}
	
	/**
	 * 単純なテーブルを1つemitして確認。
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void test02_単純なテーブルを1つemitして確認() throws Exception {
		SimpleDataType varchar32 = new SimpleDataType(VARCHAR);
		varchar32.putParam(TypeParameterKey.SIZE, 32);
		
		// FORMAT-OFF
		JmTable table = new JmTableBuilder("T_FOO")
				.with(new JmColumnBuilder("ID").type(new SimpleDataType(INTEGER)).build())
				.with(new JmColumnBuilder("NAME").type(varchar32).build())
				.with(new JmColumnBuilder("HOGE").type(new SimpleDataType(INTEGER)).build())
				.build();
		// FORMAT-ON
		context.store(table);
		
		List<SqlStatement> statements = emitter.emit(context, config);
		assertThat(statements.size(), is(2));
		assertThat(statements.get(0).toString(), is("DROP TABLE T_FOO;"));
		assertThat(statements.get(1).toString(), is("CREATE TABLE T_FOO(ID INTEGER, NAME VARCHAR(32), HOGE INTEGER);"));
	}
}
