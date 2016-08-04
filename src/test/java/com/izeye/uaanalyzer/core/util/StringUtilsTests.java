/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.izeye.uaanalyzer.core.util;

import java.util.Set;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for StringUtils.
 *
 * @author Johnny Lim
 */
public class StringUtilsTests {

	@Test
	public void testDelimitedListToSet() {
		String value = "Windows NT 6.1; WOW64; Trident/7.0; rv:11.0";
		Set<String> fields = StringUtils.delimitedListToSet(value, ";");
		assertThat(fields).containsExactlyInAnyOrder(
				"Windows NT 6.1", "WOW64", "Trident/7.0", "rv:11.0");
	}

}
