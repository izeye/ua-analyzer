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

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Analyzed user agent information.
 *
 * @author Johnny Lim
 */
@Data
@NoArgsConstructor
public class UserAgent implements Comparable<UserAgent> {

	/**
	 * UserAgent instance indicating that resolution has been failed.
	 */
	public static final UserAgent NOT_AVAILABLE = new UserAgent();

	private String raw;

	private String mozilla;
	private String systemAndBrowserInformation;
	private String platform;
	private String platformDetails;
	private String extensions;

	private OsInfo osInfo = OsInfo.NOT_AVAILABLE;
	private BrowserInfo browserInfo = BrowserInfo.NOT_AVAILABLE;

	public UserAgent(String raw) {
		this.raw = raw;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		UserAgent other = (UserAgent) o;
		if (osInfo != OsInfo.NOT_AVAILABLE && browserInfo != BrowserInfo.NOT_AVAILABLE) {
			return osInfo.equals(other.osInfo) && browserInfo.equals(other.browserInfo);
		}
		return raw.equals(other.raw);
	}

	@Override
	public int hashCode() {
		if (osInfo != OsInfo.NOT_AVAILABLE && browserInfo != BrowserInfo.NOT_AVAILABLE) {
			return osInfo.hashCode() * 31 + browserInfo.hashCode();
		}
		return raw.hashCode();
	}

	@Override
	public int compareTo(UserAgent o) {
		if (osInfo != OsInfo.NOT_AVAILABLE && browserInfo != BrowserInfo.NOT_AVAILABLE) {
			int result = osInfo.compareTo(o.osInfo);
			if (result != 0) {
				return result;
			}
			return browserInfo.compareTo(o.browserInfo);
		}
		return ObjectUtils.compare(raw, o.raw);
	}

	public String toPrettyString() {
		StringBuilder sb = new StringBuilder();
		sb.append(osInfo == OsInfo.NOT_AVAILABLE ? "N/A" : osInfo.toPrettyString());
		sb.append(", ");
		sb.append(browserInfo == BrowserInfo.NOT_AVAILABLE ? "N/A" : browserInfo.toPrettyString());
		return sb.toString();
	}

}
