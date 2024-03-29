/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2008/09/10
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

import org.jiemamy.validator.impl.AbstractIdentifierValidator;

/**
 * PostgreSQL用識別子バリデータ。
 * 
 * @author daisuke
 */
public class PostgreSqlIdentifierValidator extends AbstractIdentifierValidator {
	
	/**
	 * インスタンスを生成する。
	 */
	public PostgreSqlIdentifierValidator() {
		super("^[0-9a-zA-Z_]+$", new PostgreSqlReservedWordsChecker());
	}
	
}
