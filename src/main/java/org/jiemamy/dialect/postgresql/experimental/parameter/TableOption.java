/*
 * Copyright 2007-2009 Jiemamy Project and the Others.
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

import org.jiemamy.model.table.JmTable;

/**
 * {@link JmTable}に対するオプションモデル。
 * 
 * @author daisuke
 */
public interface TableOption extends PostgresObjectOption {
	
	/**
	 * 一時テーブルに対してコミット時に行われるアクションを取得する。
	 * 
	 * @return 一時テーブルに対してコミット時に行われるアクション
	 */
	CommitAction getCommitAction();
	
	/**
	 * 一時テーブルかどうかを取得する。
	 * 
	 * @return 一時テーブルの場合は{@code true}、そうでない場合は{@code false}
	 */
	Boolean isTemporary();
	
}
