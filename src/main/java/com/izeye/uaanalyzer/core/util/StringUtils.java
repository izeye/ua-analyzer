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
