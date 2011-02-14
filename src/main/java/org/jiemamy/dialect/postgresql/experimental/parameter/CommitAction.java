/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
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
 * トランザクションブロックの終了時点での一時テーブルの動作を制御する指示。
 * 
 * @author daisuke
 */
public enum CommitAction {
	
	/** トランザクションの終了時点で、特別な動作は行わない */
	PRESERVE_ROWS,

	/** 一時テーブル内の全ての行は、各トランザクションブロックの終わりで削除 */
	DELETE_ROWS,

	/** 一時テーブルは、現在のトランザクションブロックの終了時点で削除 */
	DROP
}
