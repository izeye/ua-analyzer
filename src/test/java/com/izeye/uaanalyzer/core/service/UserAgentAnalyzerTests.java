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

package com.izeye.uaanalyzer.core.service;

import com.izeye.uaanalyzer.core.domain.BrowserInfo;
import com.izeye.uaanalyzer.core.domain.BrowserType;
import com.izeye.uaanalyzer.core.domain.OsInfo;
import com.izeye.uaanalyzer.core.domain.OsType;
import com.izeye.uaanalyzer.core.domain.UserAgent;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for UserAgentAnalyzer.
 *
 * @author Johnny Lim
 */
public class UserAgentAnalyzerTests {

	private UserAgentAnalyzer userAgentAnalyzer;

	@Before
	public void setUp() {
		this.userAgentAnalyzer = new DefaultUserAgentAnalyzer();
	}

	@Test
	public void testAnalyzeWithIE11() {
		String userAgentString = "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.WINDOWS);
		assertThat(osInfo.getOsVersion()).isEqualTo("NT 6.1");
		assertThat(osInfo.getDescription()).isEqualTo("Windows 7");

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.IE);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("11");
	}

	@Test
	public void testAnalyzeWithEdge() {
		String userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.WINDOWS);
		assertThat(osInfo.getOsVersion()).isEqualTo("NT 10.0");
		assertThat(osInfo.getDescription()).isEqualTo("Windows 10");

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.EDGE);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("13.10586");
	}

	@Test
	public void testAnalyzeWithChromeWindow() {
		String userAgentString = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.WINDOWS);
		assertThat(osInfo.getOsVersion()).isEqualTo("NT 6.1");
		assertThat(osInfo.getDescription()).isEqualTo("Windows 7");

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.CHROME);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("51.0.2704.103");
	}

	@Test
	public void testAnalyzeWithChromeLinux() {
		String userAgentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/538.1 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/538.1";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.LINUX);
		assertThat(osInfo.getOsVersion()).isNull();
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.CHROME);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("50.0.2661.102");
	}

	@Test
	public void testAnalyzeWithSafariMacOsX() {
		String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/601.6.17 (KHTML, like Gecko) Version/9.1.1 Safari/601.6.17";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.MAC_OS_X);
		assertThat(osInfo.getOsVersion()).isEqualTo("10.11.5");
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.SAFARI);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("9.1.1");
	}

	@Test
	public void testAnalyzeWithSafariIOSIPhone() {
		String userAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_2 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13F69 Safari/601.1";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.I_OS);
		assertThat(osInfo.getOsVersion()).isEqualTo("9.3.2");
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.SAFARI);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("9.0");
	}

	@Test
	public void testAnalyzeWithSafariIOSIPad() {
		String userAgentString = "Mozilla/5.0 (iPad; CPU OS 9_3_2 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13F69 Safari/601.1";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.I_OS);
		assertThat(osInfo.getOsVersion()).isEqualTo("9.3.2");
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.SAFARI);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("9.0");
	}

	@Test
	public void testAnalyzeWithFirefox() {
		String userAgentString = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.WINDOWS);
		assertThat(osInfo.getOsVersion()).isEqualTo("NT 6.1");
		assertThat(osInfo.getDescription()).isEqualTo("Windows 7");

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.FIREFOX);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("47.0");
	}

	@Test
	public void testAnalyzeWithOpera() {
		String userAgentString = "Opera/9.80 (Windows NT 5.1; U; en) Presto/2.9.168 Version/11.51";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.WINDOWS);
		assertThat(osInfo.getOsVersion()).isEqualTo("NT 5.1");
		assertThat(osInfo.getDescription()).isEqualTo("Windows XP");

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.OPERA);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("11.51");
	}

	@Test
	public void testAnalyzeWithAndroidChrome() {
		String userAgentString = "Mozilla/5.0 (Linux; Android 6.0.1; SM-N920S Build/MMB29K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0.2704.81 Mobile Safari/537.36 NAVER(inapp; search; 470; 7.3.2)";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.ANDROID);
		assertThat(osInfo.getOsVersion()).isEqualTo("6.0.1");
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.CHROME);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("51.0.2704.81");
	}

	@Test
	public void testAnalyzeWithIncompleteUserAgent() {
		// NOTE: Although this user agent exists, it will be ignored for the time being.
		String userAgentString = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);
		assertThat(userAgent).isEqualTo(UserAgent.NOT_AVAILABLE);
	}

}
