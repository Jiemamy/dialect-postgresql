/*
 * Copyright 2007-2011 Jiemamy Project and the Others.
 * Created on 2011/01/29
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.InetAddress;

import org.junit.Test;

import org.jiemamy.test.AbstractDatabaseTest;

/**
 * TODO for daisuke
 * 
 * @version $Id$
 * @author daisuke
 */
public class PostgresqlDatabaseTest extends AbstractDatabaseTest {
	
	/**
	 * TODO for daisuke
	 * 
	 * @throws Exception 例外が発生した場合
	 */
	@Test
	public void testname() throws Exception {
		System.out.print(InetAddress.getLocalHost().getHostName());
		assertThat(getPassword(), is("****"));
	}
	
}
