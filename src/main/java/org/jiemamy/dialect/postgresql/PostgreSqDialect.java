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

import static org.jiemamy.model.datatype.RawTypeCategory.BIT;
import static org.jiemamy.model.datatype.RawTypeCategory.BLOB;
import static org.jiemamy.model.datatype.RawTypeCategory.BOOLEAN;
import static org.jiemamy.model.datatype.RawTypeCategory.CHARACTER;
import static org.jiemamy.model.datatype.RawTypeCategory.CLOB;
import static org.jiemamy.model.datatype.RawTypeCategory.DATE;
import static org.jiemamy.model.datatype.RawTypeCategory.DECIMAL;
import static org.jiemamy.model.datatype.RawTypeCategory.DOUBLE;
import static org.jiemamy.model.datatype.RawTypeCategory.FLOAT;
import static org.jiemamy.model.datatype.RawTypeCategory.INTEGER;
import static org.jiemamy.model.datatype.RawTypeCategory.INTERVAL;
import static org.jiemamy.model.datatype.RawTypeCategory.NUMERIC;
import static org.jiemamy.model.datatype.RawTypeCategory.OTHER;
import static org.jiemamy.model.datatype.RawTypeCategory.REAL;
import static org.jiemamy.model.datatype.RawTypeCategory.SMALLINT;
import static org.jiemamy.model.datatype.RawTypeCategory.TIME;
import static org.jiemamy.model.datatype.RawTypeCategory.TIMESTAMP;
import static org.jiemamy.model.datatype.RawTypeCategory.VARBIT;
import static org.jiemamy.model.datatype.RawTypeCategory.VARCHAR;

import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

import org.jiemamy.dialect.AbstractDialect;
import org.jiemamy.dialect.DatabaseMetadataParser;
import org.jiemamy.dialect.Necessity;
import org.jiemamy.dialect.SqlEmitter;
import org.jiemamy.model.datatype.SimpleRawTypeDescriptor;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.validator.CompositeValidator;
import org.jiemamy.validator.Validator;

/**
 * PostgreSQLに対するSQL方言実装クラス。
 * 
 * @author daisuke
 */
@SuppressWarnings("serial")
public class PostgreSqDialect extends AbstractDialect {
	
	private static List<Entry> typeEntries = Lists.newArrayList();
	
	static {
		// FORMAT-OFF
		// CHECKSTYLE:OFF
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(INTEGER, "INTEGER", "int4"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(INTEGER, "BIGINT", "int8"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SERIAL, Necessity.OPTIONAL);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(SMALLINT)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(NUMERIC),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DECIMAL)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(FLOAT, "REAL"),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.PRECISION, Necessity.REQUIRED);
						put(TypeParameterKey.SCALE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(REAL)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DOUBLE, "DOUBLE PRECISION")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BIT),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(CHARACTER),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(VARCHAR),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(CLOB, "TEXT")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BLOB, "BYTEA")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(VARBIT),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.SIZE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(BOOLEAN)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(DATE)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIME),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(TIMESTAMP),
				new HashMap<TypeParameterKey<?>, Necessity>() {{
						put(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED);
				}}));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(INTERVAL)));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "UUID")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "MACADDR")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "MONEY")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "INET")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "CIDR")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "XML")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "LINE")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "LSEG")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "CIRCLE")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "BOX")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "PATH")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "POINT")));
		typeEntries.add(new Entry(new SimpleRawTypeDescriptor(OTHER, "POLYGON")));
		// CHECKSTYLE:ON
		// FORMAT-ON
	}
	

	/**
	 * インスタンスを生成する。
	 */
	public PostgreSqDialect() {
		super("jdbc:postgresql://localhost:5432/", typeEntries);
	}
	
	public DatabaseMetadataParser getDatabaseMetadataParser() {
		return new PostgreSqlDatabaseMetadataParser(this);
	}
	
	public String getName() {
		return "PostgreSQL 8.1";
	}
	
	public SqlEmitter getSqlEmitter() {
		return new PostgreSqlEmitter(this, new PostgreSqlTokenResolver());
	}
	
	@Override
	public Validator getValidator() {
		CompositeValidator validator = (CompositeValidator) super.getValidator();
		validator.getValidators().add(new PostgreSqIdentifierValidator());
		return validator;
	}
}
