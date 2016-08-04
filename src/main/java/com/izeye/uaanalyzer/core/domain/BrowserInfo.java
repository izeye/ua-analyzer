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

package com.izeye.uaanalyzer.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Browser information.
 *
 * @author Johnny Lim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrowserInfo implements Comparable<BrowserInfo> {

	/**
	 * BrowserInfo instance indicating that resolution has been failed.
	 */
	public static final BrowserInfo NOT_AVAILABLE = new BrowserInfo();

	/**
	 * BrowserInfo instance for Internet Explorer 8.
	 */
	public static final BrowserInfo IE_8 = new BrowserInfo(BrowserType.IE, "8");

	/**
	 * BrowserInfo instance for Internet Explorer 9.
	 */
	public static final BrowserInfo IE_9 = new BrowserInfo(BrowserType.IE, "9");

	/**
	 * BrowserInfo instance for Internet Explorer 10.
	 */
	public static final BrowserInfo IE_10 = new BrowserInfo(BrowserType.IE, "10");

	/**
	 * BrowserInfo instance for Internet Explorer 11.
	 */
	public static final BrowserInfo IE_11 = new BrowserInfo(BrowserType.IE, "11");

	private BrowserType browserType;
	private String browserVersion;

	@Override
	public int compareTo(BrowserInfo o) {
		int result = ObjectUtils.compare(browserType, o.browserType);
		if (result != 0) {
			return result;
		}
		return ObjectUtils.compare(browserVersion, o.browserVersion);
	}

	public String toPrettyString() {
		StringBuilder sb = new StringBuilder();
		sb.append(browserType.getName());
		sb.append(" ");
		sb.append(browserVersion);
		return sb.toString();
	}

}
