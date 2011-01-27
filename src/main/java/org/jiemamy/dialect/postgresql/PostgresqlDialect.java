/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
 * Created on 2008/07/14
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

import static org.jiemamy.model.datatype.DataTypeCategory.BIT;
import static org.jiemamy.model.datatype.DataTypeCategory.BLOB;
import static org.jiemamy.model.datatype.DataTypeCategory.BOOLEAN;
import static org.jiemamy.model.datatype.DataTypeCategory.CHARACTER;
import static org.jiemamy.model.datatype.DataTypeCategory.CLOB;
import static org.jiemamy.model.datatype.DataTypeCategory.DATE;
import static org.jiemamy.model.datatype.DataTypeCategory.DECIMAL;
import static org.jiemamy.model.datatype.DataTypeCategory.DOUBLE;
import static org.jiemamy.model.datatype.DataTypeCategory.FLOAT;
import static org.jiemamy.model.datatype.DataTypeCategory.INTEGER;
import static org.jiemamy.model.datatype.DataTypeCategory.INTERVAL;
import static org.jiemamy.model.datatype.DataTypeCategory.NUMERIC;
import static org.jiemamy.model.datatype.DataTypeCategory.OTHER;
import static org.jiemamy.model.datatype.DataTypeCategory.REAL;
import static org.jiemamy.model.datatype.DataTypeCategory.SMALLINT;
import static org.jiemamy.model.datatype.DataTypeCategory.TIME;
import static org.jiemamy.model.datatype.DataTypeCategory.TIMESTAMP;
import static org.jiemamy.model.datatype.DataTypeCategory.VARBIT;
import static org.jiemamy.model.datatype.DataTypeCategory.VARCHAR;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import org.jiemamy.dialect.AbstractDialect;
import org.jiemamy.dialect.DatabaseMetadataParser;
import org.jiemamy.dialect.DefaultDatabaseMetadataParser;
import org.jiemamy.dialect.SqlEmitter;
import org.jiemamy.dialect.TypeParameterSpec;
import org.jiemamy.dialect.TypeParameterSpec.Necessity;
import org.jiemamy.model.datatype.DefaultTypeReference;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.validator.CompositeValidator;
import org.jiemamy.validator.Validator;

/**
 * PostgreSQLに対するSQL方言実装クラス。
 * 
 * @author daisuke
 */
public class PostgresqlDialect extends AbstractDialect {
	
	private static List<Entry> typeEntries = Lists.newArrayList();
	
	static {
		// FORMAT-OFF
		typeEntries.add(new Entry(new DefaultTypeReference(INTEGER, "INTEGER", "int4"), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SERIAL, Necessity.OPTIONAL)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(INTEGER, "BIGINT", "int8"), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SERIAL, Necessity.OPTIONAL)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(SMALLINT)));
		typeEntries.add(new Entry(new DefaultTypeReference(NUMERIC), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.PRECISION, Necessity.REQUIRED),
			new TypeParameterSpec(TypeParameterKey.SCALE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(DECIMAL)));
		typeEntries.add(new Entry(new DefaultTypeReference(FLOAT, "REAL"), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.PRECISION, Necessity.REQUIRED),
			new TypeParameterSpec(TypeParameterKey.SCALE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(REAL)));
		typeEntries.add(new Entry(new DefaultTypeReference(DOUBLE, "DOUBLE PRECISION")));
		typeEntries.add(new Entry(new DefaultTypeReference(BIT), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(CHARACTER), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(VARCHAR), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(CLOB, "TEXT")));
		typeEntries.add(new Entry(new DefaultTypeReference(BLOB, "BYTEA")));
		typeEntries.add(new Entry(new DefaultTypeReference(VARBIT), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(BOOLEAN)));
		typeEntries.add(new Entry(new DefaultTypeReference(DATE)));
		typeEntries.add(new Entry(new DefaultTypeReference(TIME), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(TIMESTAMP), Arrays.asList(
			new TypeParameterSpec(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED)
		)));
		typeEntries.add(new Entry(new DefaultTypeReference(INTERVAL)));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "UUID")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "MACADDR")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "MONEY")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "INET")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "CIDR")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "XML")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "LINE")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "LSEG")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "CIRCLE")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "BOX")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "PATH")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "POINT")));
		typeEntries.add(new Entry(new DefaultTypeReference(OTHER, "POLYGON")));
		// FORMAT-ON
	}
	

	/**
	 * インスタンスを生成する。
	 */
	public PostgresqlDialect() {
		super("jdbc:postgresql://localhost:5432/", typeEntries);
	}
	
	public DatabaseMetadataParser getDatabaseMetadataParser() {
		// TODO カスタマイズ
		return new DefaultDatabaseMetadataParser(this);
	}
	
	public String getName() {
		return "PostgreSQL 8.1";
	}
	
	public SqlEmitter getSqlEmitter() {
		return new PostgreSqlEmitter(this);
	}
	
	@Override
	public Validator getValidator() {
		CompositeValidator validator = (CompositeValidator) super.getValidator();
		validator.getValidators().add(new PostgresqlIdentifierValidator());
		return validator;
	}
}
