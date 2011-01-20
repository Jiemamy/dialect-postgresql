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

import org.jiemamy.model.index.IndexModel;
import org.jiemamy.model.parameter.Converter;
import org.jiemamy.model.parameter.ParameterKey;

/**
 * {@link IndexModel}に対するPostgreSQL特有の設定項目実装クラス。
 * 
 * @author daisuke
 */
public final class DefaultIndexOption extends AbstractPostgresObjectOption implements IndexOption {
	
	public static final ParameterKey<DefaultIndexOption> KEY = new ParameterKey<DefaultIndexOption>(
			new Converter<DefaultIndexOption>() {
				
				public String toString(DefaultIndexOption obj) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public DefaultIndexOption valueOf(String str) {
					// TODO Auto-generated method stub
					return null;
				}
			}, "org.jiemamy.ns.postgresql.indexOption");
	
	/** インデックス構築アルゴリズム */
	private IndexMethodType indexMethodType;
	
	private Boolean concurrently;
	
	private Integer fillfactor;
	
	/** 部分インデックス生成用条件 */
	private String predicate;
	

	public Boolean getConcurrently() {
		return concurrently;
	}
	
	public Integer getFillfactor() {
		return fillfactor;
	}
	
	public IndexMethodType getIndexMethodType() {
		return indexMethodType;
	}
	
	public String getPredicate() {
		return predicate;
	}
	
	/**
	 * 対象テーブルに対する同時挿入、更新、削除を防止するようなロックを獲得せずにインデックスを作成するかどうかを設定する。
	 * 
	 * @param concurrently 対象テーブルに対する同時挿入、更新、削除を防止するようなロックを獲得せずにインデックスを作成する場合{@code true}
	 */
	public void setConcurrently(Boolean concurrently) {
		this.concurrently = concurrently;
	}
	
	/**
	 * fillfactorを設定する。
	 * 
	 * <p>インデックスメソッドがインデックスページをまとめ上げる時にどの程度ページを使用するかを決定するための値。0～100(%)の範囲で指定する。</p>
	 * 
	 * @param fillfactor fillfactor
	 */
	public void setFillfactor(Integer fillfactor) {
		this.fillfactor = fillfactor;
	}
	
	/**
	 * インデックス構築アルゴリズムを設定する。
	 * 
	 * @param indexMethodType インデックス構築アルゴリズム
	 */
	public void setIndexMethodType(IndexMethodType indexMethodType) {
		this.indexMethodType = indexMethodType;
	}
	
	/**
	 * 部分インデックス生成用条件を設定する。
	 * 
	 * @param predicate 部分インデックス生成用条件
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
}
