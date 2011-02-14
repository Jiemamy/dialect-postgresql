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

import org.jiemamy.model.index.JmIndex;

/**
 * {@link JmIndex}に対するPostgreSQL特有の設定項目インターフェイス。
 * 
 * @author daisuke
 */
public interface IndexOption extends PostgresObjectOption {
	
	/**
	 * 対象テーブルに対する同時挿入、更新、削除を防止するようなロックを獲得せずにインデックスを作成するかどうかを取得する。
	 * 
	 * @return 対象テーブルに対する同時挿入、更新、削除を防止するようなロックを獲得せずにインデックスを作成するかどうか
	 */
	Boolean getConcurrently();
	
	/**
	 * fillfactorを取得する。
	 * 
	 * <p>インデックスメソッドがインデックスページをまとめ上げる時にどの程度ページを使用するかを決定するための値。0～100(%)の範囲で指定する。</p>
	 * 
	 * @return fillfactor
	 */
	Integer getFillfactor();
	
	/**
	 * インデックス構築アルゴリズムを取得する。
	 * 
	 * @return インデックス構築アルゴリズム
	 */
	IndexMethodType getIndexMethodType();
	
	/**
	 * 部分インデックス生成用条件を取得する。
	 * 
	 * @return 部分インデックス生成用条件
	 */
	String getPredicate();
	
}
