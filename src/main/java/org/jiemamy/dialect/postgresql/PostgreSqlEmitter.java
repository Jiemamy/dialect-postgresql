/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
 * Created on 2009/02/24
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

import java.util.List;

import com.google.common.collect.Lists;

import org.jiemamy.JiemamyContext;
import org.jiemamy.dialect.DefaultSqlEmitter;
import org.jiemamy.dialect.Dialect;
import org.jiemamy.dialect.SqlEmitter;
import org.jiemamy.dialect.TokenResolver;
import org.jiemamy.dialect.postgresql.experimental.parameter.DefaultIndexOption;
import org.jiemamy.dialect.postgresql.experimental.parameter.IndexMethodType;
import org.jiemamy.dialect.postgresql.experimental.parameter.IndexOption;
import org.jiemamy.model.DatabaseObjectModel;
import org.jiemamy.model.index.IndexColumnModel;
import org.jiemamy.model.index.IndexModel;
import org.jiemamy.model.sql.DefaultSqlStatement;
import org.jiemamy.model.sql.Identifier;
import org.jiemamy.model.sql.Keyword;
import org.jiemamy.model.sql.Literal;
import org.jiemamy.model.sql.Separator;
import org.jiemamy.model.sql.SqlStatement;
import org.jiemamy.model.sql.Token;
import org.jiemamy.model.table.TableModel;

/**
 * PostgreSQL用の{@link SqlEmitter}実装クラス。
 * 
 * @author daisuke
 */
public class PostgreSqlEmitter extends DefaultSqlEmitter {
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dialect {@link Dialect}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public PostgreSqlEmitter(Dialect dialect) {
		super(dialect);
	}
	
	/**
	 * インスタンスを生成する。
	 * 
	 * @param dialect {@link Dialect}
	 * @param tokenResolver {@link TokenResolver}
	 * @throws IllegalArgumentException 引数に{@code null}を与えた場合
	 */
	public PostgreSqlEmitter(Dialect dialect, TokenResolver tokenResolver) {
		super(dialect, tokenResolver);
	}
	
	@Override
	protected SqlStatement emitCreateIndexStatement(JiemamyContext context, TableModel tableModel, IndexModel indexModel) {
		IndexOption indexOption = indexModel.getParam(DefaultIndexOption.KEY);
		if (indexOption == null) {
			return super.emitCreateIndexStatement(context, tableModel, indexModel);
		}
		
		List<Token> tokens = Lists.newArrayList();
		tokens.add(Keyword.CREATE);
		if (indexModel.isUnique()) {
			tokens.add(Keyword.UNIQUE);
		}
		tokens.add(Keyword.INDEX);
		
		if (indexOption.getConcurrently() != null && indexOption.getConcurrently() == true) {
			tokens.add(Keyword.of("CONCURRENTLY"));
		}
		
		tokens.add(Identifier.of(indexModel.getName()));
		tokens.add(Keyword.ON);
		tokens.add(Identifier.of(tableModel.getName()));
		
		if (indexOption.getIndexMethodType() != null) {
			IndexMethodType indexMethodType = indexOption.getIndexMethodType();
			tokens.add(Keyword.of("USING"));
			tokens.add(Identifier.of(indexMethodType.toString()));
		}
		
		tokens.add(Separator.LEFT_PAREN);
		
		for (IndexColumnModel indexColumnModel : indexModel.getIndexColumns()) {
			tokens.addAll(emitIndexColumnClause(context, indexColumnModel));
		}
		
		if (indexModel.getIndexColumns().isEmpty() == false) {
			tokens.remove(tokens.size() - 1);
		}
		tokens.add(Separator.RIGHT_PAREN);
		
		if (indexOption.getFillfactor() != null) {
			tokens.add(Keyword.WITH);
			tokens.add(Separator.LEFT_PAREN);
			tokens.add(Identifier.of("fillfactor"));
			tokens.add(Separator.EQUAL);
			tokens.add(Literal.of(indexOption.getFillfactor()));
			tokens.add(Separator.RIGHT_PAREN);
		}
		if (indexOption.getTablespace() != null) {
			tokens.add(Keyword.of("TABLESPACE"));
			tokens.add(Identifier.of(indexOption.getTablespace()));
		}
		if (indexOption.getPredicate() != null) {
			tokens.add(Keyword.WHERE);
			tokens.add(Identifier.of(indexOption.getPredicate()));
		}
		
		tokens.add(Separator.SEMICOLON);
		return new DefaultSqlStatement(tokens);
	}
	
	@Override
	protected SqlStatement emitDropEntityStatement(DatabaseObjectModel entityModel) {
		SqlStatement stmt = super.emitDropEntityStatement(entityModel);
		return insertIfExists(stmt);
	}
	
	@Override
	protected SqlStatement emitDropIndexStatement(IndexModel indexModel) {
		SqlStatement stmt = super.emitDropIndexStatement(indexModel);
		return insertIfExists(stmt);
	}
	
	@Override
	protected SqlStatement emitDropSchemaStatement(String schemaName) {
		SqlStatement stmt = super.emitDropSchemaStatement(schemaName);
		return insertIfExists(stmt);
	}
	
	private SqlStatement insertIfExists(SqlStatement stmt) {
		List<Token> tokens = stmt.toTokens();
		// THINK IF EXISTSを挿入してよいか？
//		tokens.add(2, Keyword.of("IF"));
//		tokens.add(3, Keyword.of("EXISTS"));
		return new DefaultSqlStatement(tokens);
	}
}
