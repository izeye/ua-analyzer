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

import java.util.HashSet;
import java.util.Set;

/**
 * Utilities for String.
 *
 * @author Johnny Lim
 */
public abstract class StringUtils {

	private StringUtils() {
	}

	public static Set<String> delimitedListToSet(String value, String delimiter) {
		Set<String> fieldSet = new HashSet<>();
		String[] fields = value.split(delimiter);
		for (String field : fields) {
			fieldSet.add(field.trim());
		}
		return fieldSet;
	}

}
