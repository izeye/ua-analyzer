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

	public static final BrowserInfo NOT_AVAILABLE = new BrowserInfo();

	public static final BrowserInfo IE_8 = new BrowserInfo(BrowserType.IE, "8");
	public static final BrowserInfo IE_9 = new BrowserInfo(BrowserType.IE, "9");
	public static final BrowserInfo IE_10 = new BrowserInfo(BrowserType.IE, "10");
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

}
