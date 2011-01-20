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

/**
 * {@link IndexColumnOption}に対するオプションモデルの実装クラス。
 * 
 * @author daisuke
 */
public final class DefaultIndexColumOption implements IndexColumnOption {
	
	private NullOrder nullOrder;
	

	public NullOrder getNullOrder() {
		return nullOrder;
	}
	
	/**
	 * ソートの際のNULLの扱いを設定する。
	 * 
	 * @param nullOrder ソートの際のNULLの扱い
	 */
	public void setNullOrder(NullOrder nullOrder) {
		this.nullOrder = nullOrder;
	}
	
}
