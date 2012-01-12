/*
 * Copyright 2007-2012 Jiemamy Project and the Others.
 * Created on 2009/03/19
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
package org.jiemamy.dialect.postgresql.experimental.parameter;

/**
 * PostgreSQLオブジェクトの共通設定項目インターフェイス。
 * 
 * @author daisuke
 */
public interface PostgresObjectOption {
	
	/**
	 * テーブルスペース名を取得する。
	 * 
	 * @return テーブルスペース名
	 */
	String getTablespace();
	
	/**
	 * テーブルスペース名を設定する。
	 * 
	 * @param tablespace テーブルスペース名
	 */
	void setTablespace(String tablespace);
	
}
