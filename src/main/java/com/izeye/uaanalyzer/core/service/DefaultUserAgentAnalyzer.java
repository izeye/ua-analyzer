package com.izeye.uaanalyzer.core.service;

import com.izeye.uaanalyzer.core.domain.BrowserInfo;
import com.izeye.uaanalyzer.core.domain.BrowserType;
import com.izeye.uaanalyzer.core.domain.OsInfo;
import com.izeye.uaanalyzer.core.domain.OsType;
import com.izeye.uaanalyzer.core.domain.UserAgent;
import com.izeye.uaanalyzer.core.util.StringUtils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by izeye on 16. 7. 22..
 */
@Service
public class DefaultUserAgentAnalyzer implements UserAgentAnalyzer {
	
	private static final String MOZILLA = "mozilla";
	private static final String SYSTEM_AND_BROWSER_INFORMATION = "systemAndBrowserInformation";
	private static final String PLATFORM = "platform";
	private static final String PLATFORM_DETAILS = "platformDetails";
	private static final String EXTENSIONS = "extensions";
	
	private static final String USER_AGENT_REGEX = String.format(
			"(?<%s>[^ ]+) \\((?<%s>[^\\)]+)\\)( (?<%s>[^ ]+))?( \\((?<%s>[^\\)]+)\\))?( (?<%s>.+))?",
			MOZILLA, SYSTEM_AND_BROWSER_INFORMATION, PLATFORM, PLATFORM_DETAILS, EXTENSIONS);
	private static final Pattern USER_AGENT_PATTERN = Pattern.compile(USER_AGENT_REGEX);

	private static final String LIKE_GECKO = " like Gecko";
	private static final String WINDOWS = "Windows";
	private static final String MSIE = "MSIE";

	private static final String CHROME = "Chrome";

	private static final String MAC_OS_X = "Intel Mac OS X";
	private static final int MAC_OS_X_LENGTH = MAC_OS_X.length();

	private static final String SAFARI_VERSION = "Version";
	private static final String SAFARI = "Safari";

	private static final String IRON = "Iron";

	private static final String FIREFOX = "Firefox";

	private static final String LINUX = "Linux";

	private static final String I_PHONE_OS_NAME_PREFIX = "CPU iPhone OS";
	private static final int I_PHONE_OS_NAME_PREFIX_LENGTH = I_PHONE_OS_NAME_PREFIX.length();

	private static final String I_PAD_OS_NAME_PREFIX = "CPU OS";
	private static final int I_PAD_OS_NAME_PREFIX_LENGTH = I_PAD_OS_NAME_PREFIX.length();

	private static final String PRESTO = "Presto";

	private static final String CHROME_OS = "CrOS";

	private static final Map<String, String> WINDOWS_DESCRIPTION_BY_VERSION;
	static {
		Map<String, String> windowsDescriptionByVersion = new HashMap<>();
		windowsDescriptionByVersion.put("CE", "Windows CE");
		windowsDescriptionByVersion.put("95", "Windows 95");
		windowsDescriptionByVersion.put("98", "Windows 98");
		windowsDescriptionByVersion.put("98; Win 9x 4.90", "Windows Millennium Edition (Windows Me)");
		windowsDescriptionByVersion.put("NT 4.0", "Microsoft Windows NT 4.0");
		windowsDescriptionByVersion.put("NT 5.0", "Windows 2000");
		windowsDescriptionByVersion.put("NT 5.01", "Windows 2000, Service Pack 1 (SP1)");
		windowsDescriptionByVersion.put("NT 5.1", "Windows XP");
		windowsDescriptionByVersion.put("NT 5.2", "Windows Server 2003; Windows XP x64 Edition");
		windowsDescriptionByVersion.put("NT 6.0", "Windows Vista");
		windowsDescriptionByVersion.put("NT 6.1", "Windows 7");
		windowsDescriptionByVersion.put("NT 6.2", "Windows 8");
		windowsDescriptionByVersion.put("NT 6.3", "Windows 8.1");
		WINDOWS_DESCRIPTION_BY_VERSION = Collections.unmodifiableMap(windowsDescriptionByVersion);
	}

	private static final Map<String, BrowserInfo> BROWSER_INFO_BY_TRIDENT;
	static {
		Map<String, BrowserInfo> browserInfoByTrident = new HashMap<>();
		browserInfoByTrident.put("Trident/4.0", BrowserInfo.IE_8);
		browserInfoByTrident.put("Trident/5.0", BrowserInfo.IE_9);
		browserInfoByTrident.put("Trident/6.0", BrowserInfo.IE_10);
		browserInfoByTrident.put("Trident/7.0", BrowserInfo.IE_11);
		BROWSER_INFO_BY_TRIDENT = Collections.unmodifiableMap(browserInfoByTrident);
	}

	@Override
	public UserAgent analyze(String userAgentString) {
		String sanitized = sanitize(userAgentString);

		Matcher matcher = USER_AGENT_PATTERN.matcher(sanitized);
		if (!matcher.find()) {
			return UserAgent.NOT_AVAILABLE;
		}

		UserAgent userAgent = new UserAgent(userAgentString);
		userAgent.setMozilla(matcher.group(MOZILLA));
		String systemAndBrowserInformation = matcher.group(SYSTEM_AND_BROWSER_INFORMATION);
		userAgent.setSystemAndBrowserInformation(systemAndBrowserInformation);
		String platform = matcher.group(PLATFORM);
		userAgent.setPlatform(platform);
		userAgent.setPlatformDetails(matcher.group(PLATFORM_DETAILS));
		String extensions = matcher.group(EXTENSIONS);
		userAgent.setExtensions(extensions);
		Set<String> systemAndBrowserInformationFields =
				StringUtils.delimitedListToSet(systemAndBrowserInformation, "[;,]");
		OsInfo osInfo = resolveOsInfo(systemAndBrowserInformationFields);
		userAgent.setOsInfo(osInfo);
		BrowserInfo browserInfo = resolveBrowserInfo(
				systemAndBrowserInformationFields, platform, extensions);
		userAgent.setBrowserInfo(browserInfo);
		return userAgent;
	}

	private String sanitize(String userAgentString) {
		return userAgentString.replace(LIKE_GECKO, "");
	}

	private OsInfo resolveOsInfo(Set<String> systemAndBrowserInformationFields) {
		for (String field : systemAndBrowserInformationFields) {
			if (field.startsWith(WINDOWS)) {
				String osVersion = field.substring(
						field.indexOf(' ') + 1);
				String osDescription = WINDOWS_DESCRIPTION_BY_VERSION.get(osVersion);
				return new OsInfo(OsType.WINDOWS, osVersion, osDescription);
			}
			if (field.startsWith(MAC_OS_X)) {
				String osVersion = field.substring(MAC_OS_X_LENGTH + 1).replace("_", ".");
				return new OsInfo(OsType.MAC_OS_X, osVersion);
			}
			if (field.startsWith(LINUX)) {
				return new OsInfo(OsType.LINUX);
			}
			if (field.startsWith(I_PHONE_OS_NAME_PREFIX)) {
				return createIOSInfo(field, I_PHONE_OS_NAME_PREFIX_LENGTH);
			}
			if (field.startsWith(I_PAD_OS_NAME_PREFIX)) {
				return createIOSInfo(field, I_PAD_OS_NAME_PREFIX_LENGTH);
			}
			if (field.startsWith(CHROME_OS)) {
				return new OsInfo(OsType.CHROME_OS);
			}
		}
		System.out.println("Failed to resolve OS information.");
		return OsInfo.NOT_AVAILABLE;
	}

	private OsInfo createIOSInfo(String field, int osNamePrefixLength) {
		int osVersionStartIndex = osNamePrefixLength + 1;
		int osVersionEndIndex = field.indexOf(' ', osVersionStartIndex + 1);
		String osVersion =
				field.substring(osVersionStartIndex, osVersionEndIndex).replace("_", ".");
		return new OsInfo(OsType.I_OS, osVersion);
	}

	private BrowserInfo resolveBrowserInfo(
			Set<String> systemAndBrowserInformationFields, String platform, String extensions) {
		for (String field : systemAndBrowserInformationFields) {
			if (BROWSER_INFO_BY_TRIDENT.containsKey(field)) {
				return BROWSER_INFO_BY_TRIDENT.get(field);
			}
			if (field.startsWith(MSIE)) {
				String browserVersion = field.substring(
						field.indexOf(' ') + 1);
				return new BrowserInfo(BrowserType.IE, browserVersion);
			}
		}
		if (extensions != null) {
			String[] extensionFields = extensions.split(" ");
			if (extensionFields.length > 0) {
				for (String extensionField : extensionFields) {
					if (extensionField.startsWith(CHROME)) {
						return createBrowserInfo(extensionField, BrowserType.CHROME);
					}
				}
				String extension1 = extensionFields[0];
				if (extension1.startsWith(IRON)) {
					return createBrowserInfo(extension1, BrowserType.IRON);
				}
				if (extension1.startsWith(FIREFOX)) {
					return createBrowserInfo(extension1, BrowserType.FIREFOX);
				}
				if (platform.startsWith(PRESTO)) {
					return createBrowserInfo(extension1, BrowserType.OPERA);
				}
				if (extensionFields.length > 1) {
					String extension2 = extensionFields[1];
					if (extension1.startsWith(SAFARI_VERSION)) {
						if (extension2.startsWith(SAFARI)) {
							return createBrowserInfo(extension1, BrowserType.SAFARI);
						}
						if (extensionFields.length > 2) {
							String extension3 = extensionFields[2];
							if (extension3.startsWith(SAFARI)) {
								return createBrowserInfo(extension1, BrowserType.SAFARI);
							}
						}
					}
				}
			}
		}
		System.out.println("Failed to resolve browser information.");
		return BrowserInfo.NOT_AVAILABLE;
	}

	private BrowserInfo createBrowserInfo(String browserExtension, BrowserType browserType) {
		String browserVersion = browserExtension.split("/")[1];
		return new BrowserInfo(browserType, browserVersion);
	}

	@Override
	public void analyze(File userAgentListFile) {
		long total = 0;
		Map<UserAgent, Long> countByUserAgent = new TreeMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader((userAgentListFile)))) {
			String line;
			while ((line = br.readLine()) != null) {
				UserAgent analyzed = analyze(line);
				Long count = countByUserAgent.get(analyzed);
				countByUserAgent.put(analyzed, count == null ? 1 : count + 1);
				total++;
			}
		}
		catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		List<Map.Entry<UserAgent, Long>> entries = new ArrayList(countByUserAgent.entrySet());
		Collections.sort(entries, (o1, o2) -> -o1.getValue().compareTo(o2.getValue()));
		long accumulated = 0;
		for (Map.Entry<UserAgent, Long> entry : entries) {
			UserAgent userAgent = entry.getKey();
			Long count = entry.getValue();
			accumulated += count;
			System.out.printf(
					"%d (%.1f%%, %.1f%%): %s, %s, %s%n",
					count, count * 100d / total, accumulated * 100d / total, userAgent.getOsInfo(), userAgent.getBrowserInfo(), userAgent.getRaw());
		}
	}
	
}
