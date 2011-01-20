/*
 * Copyright 2007-2009 Jiemamy Project and the Others. Created on 2008/07/14
 * 
 * This file is part of Jiemamy.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.jiemamy.dialect.AbstractDialect;
import org.jiemamy.dialect.TypeParameterSpec;
import org.jiemamy.dialect.TypeParameterSpec.Necessity;
import org.jiemamy.model.datatype.DefaultTypeReference;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.model.datatype.TypeReference;
import org.jiemamy.validator.CompositeValidator;
import org.jiemamy.validator.Validator;

/**
 * PostgreSQLに対するSQL方言実装クラス。
 * 
 * @author daisuke
 */
public class PostgresqlDialect extends AbstractDialect {
	
	private static Map<TypeReference, TypeParameterSpec[]> types;
	
	static {
		Map<TypeReference, TypeParameterSpec[]> map = Maps.newLinkedHashMap();
		map.put(new DefaultTypeReference(INTEGER, "INTEGER", "int4"), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SERIAL, Necessity.OPTIONAL),
		});
		map.put(new DefaultTypeReference(INTEGER, "BIGINT", "int8"), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SERIAL, Necessity.OPTIONAL),
		});
		map.put(new DefaultTypeReference(SMALLINT), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(NUMERIC), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.PRECISION, Necessity.REQUIRED),
			new TypeParameterSpec(TypeParameterKey.SCALE, Necessity.REQUIRED)
		});
		map.put(new DefaultTypeReference(DECIMAL), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(FLOAT, "REAL"), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.PRECISION, Necessity.REQUIRED),
			new TypeParameterSpec(TypeParameterKey.SCALE, Necessity.REQUIRED)
		});
		map.put(new DefaultTypeReference(REAL), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(DOUBLE, "DOUBLE PRECISION"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(BIT), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(CHARACTER), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(VARCHAR), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(CLOB, "TEXT"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(BLOB, "BYTEA"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(VARBIT), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.SIZE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(BOOLEAN), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(DATE), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(TIME), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(TIMESTAMP), new TypeParameterSpec[] {
			new TypeParameterSpec(TypeParameterKey.WITH_TIMEZONE, Necessity.REQUIRED),
		});
		map.put(new DefaultTypeReference(INTERVAL), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "UUID"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "MACADDR"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "MONEY"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "INET"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "CIDR"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "XML"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "LINE"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "LSEG"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "CIRCLE"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "BOX"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "PATH"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "POINT"), new TypeParameterSpec[0]);
		map.put(new DefaultTypeReference(OTHER, "POLYGON"), new TypeParameterSpec[0]);
		PostgresqlDialect.types = ImmutableMap.copyOf(map);
	}
	

	/**
	 * インスタンスを生成する。
	 */
	public PostgresqlDialect() {
		super("jdbc:postgresql://localhost:5432/");
	}
	
	public List<TypeReference> getAllTypeReferences() {
		return Lists.newArrayList(types.keySet());
	}
	
	public String getName() {
		return "PostgreSQL 8.1";
	}
	
	public Collection<TypeParameterSpec> getTypeParameterSpecs(TypeReference reference) {
		return Lists.newArrayList(types.get(reference));
	}
	
	@Override
	public Validator getValidator() {
		CompositeValidator validator = (CompositeValidator) super.getValidator();
		validator.getValidators().add(new PostgresqlIdentifierValidator());
		return validator;
	}
}
