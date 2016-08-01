package com.izeye.uaanalyzer.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Created by izeye on 16. 7. 22..
 */
@Data
@NoArgsConstructor
public class UserAgent implements Comparable<UserAgent> {

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

}
