# ua-analyzer

## Overview
* `ua-analyzer` is an analyzer for user agents.

## How To Use

* Install to Maven local:

```
./gradlew clean publishCorePublicationToMavenLocal
```

* Add to `build.gradle`:

```
    compile("com.izeye.ua-analyzer:ua-analyzer-core:0.1.0.RELEASE")
```

* Sample source:

```
import com.izeye.uaanalyzer.core.domain.BrowserInfo;
import com.izeye.uaanalyzer.core.domain.BrowserType;
import com.izeye.uaanalyzer.core.domain.OsInfo;
import com.izeye.uaanalyzer.core.domain.OsType;
import com.izeye.uaanalyzer.core.domain.UserAgent;
import com.izeye.uaanalyzer.core.service.DefaultUserAgentAnalyzer;
import com.izeye.uaanalyzer.core.service.UserAgentAnalyzer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAgentAnalyzerTests {

	UserAgentAnalyzer userAgentAnalyzer = new DefaultUserAgentAnalyzer();

	@Test
	public void testAnalyze() {
		String userAgentString = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
		UserAgent userAgent = this.userAgentAnalyzer.analyze(userAgentString);
		System.out.println(userAgent);

		OsInfo osInfo = userAgent.getOsInfo();
		assertThat(osInfo.getOsType()).isEqualTo(OsType.MAC_OS_X);
		assertThat(osInfo.getOsVersion()).isEqualTo("10.10.5");
		assertThat(osInfo.getDescription()).isNull();

		BrowserInfo browserInfo = userAgent.getBrowserInfo();
		assertThat(browserInfo.getBrowserType()).isEqualTo(BrowserType.CHROME);
		assertThat(browserInfo.getBrowserVersion()).isEqualTo("51.0.2704.103");
	}

}
```

* Sample source output:

```
UserAgent(raw=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36, mozilla=Mozilla/5.0, systemAndBrowserInformation=Macintosh; Intel Mac OS X 10_10_5, platform=AppleWebKit/537.36, platformDetails=KHTML,, extensions=Chrome/51.0.2704.103 Safari/537.36, osInfo=OsInfo(osType=MAC_OS_X, osVersion=10.10.5, description=null), browserInfo=BrowserInfo(browserType=CHROME, browserVersion=51.0.2704.103))
```
