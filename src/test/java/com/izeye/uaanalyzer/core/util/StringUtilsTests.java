package com.izeye.uaanalyzer.core.util;

import java.util.Set;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Fill me.
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
