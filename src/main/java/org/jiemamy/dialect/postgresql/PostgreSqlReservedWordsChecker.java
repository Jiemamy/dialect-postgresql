/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2008/08/24
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

import java.util.Arrays;
import java.util.List;

import org.jiemamy.dialect.ReservedWordsChecker;

/**
 * PostgreSQLの予約語をチェックするクラス。
 * 
 * @author daisuke
 */
public class PostgreSqlReservedWordsChecker implements ReservedWordsChecker {
	
	private static final List<String> RESERVED_WORDS = Arrays.asList("ALL", "ANALYSE", "ANALYZE", "AND", "ANY",
			"ARRAY", "AS", "ASC", "ASYMMETRIC", "BOTH", "CASE", "CAST", "CHECK", "COLLATE", "COLUMN", "CONSTRAINT",
			"CREATE", "CURRENT_DATE", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "DEFAULT",
			"DEFERRABLE", "DESC", "DISTINCT", "DO", "ELSE", "END", "EXCEPT", "FOR", "FOREIGN", "FROM", "GRANT",
			"GROUP", "HAVING", "IN", "INITIALLY", "INTERSECT", "INTO", "LEADING", "LIMIT", "LOCALTIME",
			"LOCALTIMESTAMP", "NEW", "NOT", "NULL", "OFF", "OFFSET", "OLD", "ON", "ONLY", "OR", "ORDER", "PLACING",
			"PRIMARY", "REFERENCES", "RETURNING", "SELECT", "SESSION_USER", "SOME", "SYMMETRIC", "TABLE", "THEN", "TO",
			"TRAILING", "UNION", "UNIQUE", "USER", "USING", "WHEN", "WHERE", "FALSE", "TRUE", "AUTHORIZATION",
			"BETWEEN", "BINARY", "CROSS", "FREEZE", "FULL", "ILIKE", "INNER", "IS", "ISNULL", "JOIN", "LEFT", "LIKE",
			"NATURAL", "NOTNULL", "OUTER", "OVERLAPS", "RIGHT", "SIMILAR", "VERBOSE");
	
	
	public boolean isReserved(String name) {
		return RESERVED_WORDS.contains(name);
	}
	
}
