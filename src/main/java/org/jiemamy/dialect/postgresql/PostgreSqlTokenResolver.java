/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2011/02/11
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

import org.apache.commons.lang.Validate;

import org.jiemamy.dialect.DefaultTokenResolver;
import org.jiemamy.dialect.TokenResolver;
import org.jiemamy.model.datatype.DataType;
import org.jiemamy.model.datatype.TypeParameterKey;
import org.jiemamy.model.sql.Keyword;
import org.jiemamy.model.sql.Token;

/**
 * PostgreSQL用の{@link TokenResolver}実装。
 * 
 * @version $Id$
 * @author daisuke
 */
public class PostgreSqlTokenResolver extends DefaultTokenResolver {
	
	@Override
	protected List<Token> resolveType(DataType type) {
		Validate.notNull(type);
		Boolean serial = type.getParam(TypeParameterKey.SERIAL);
		if (serial != null && serial == true) {
			List<Token> result = Lists.newArrayListWithExpectedSize(5);
			if (type.getRawTypeDescriptor().getTypeName().equals("INTEGER")) {
				result.add(Keyword.of("SERIAL"));
				return result;
			} else if (type.getRawTypeDescriptor().getTypeName().equals("BIGINT")) {
				result.add(Keyword.of("BIGSERIAL"));
				return result;
			}
		}
		return super.resolveType(type);
	}
}
