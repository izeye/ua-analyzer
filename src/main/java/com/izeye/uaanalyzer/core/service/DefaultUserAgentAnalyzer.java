package com.izeye.uaanalyzer.core.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by izeye on 16. 7. 22..
 */
@Service
public class DefaultUserAgentAnalyzer implements UserAgentAnalyzer {
	
	@Override
	public void analyze(File userAgentListFile) {
		long total = 0;
		Map<String, Long> countByUserAgent = new TreeMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader((userAgentListFile)))) {
			String line;
			while ((line = br.readLine()) != null) {
				Long count = countByUserAgent.get(line);
				countByUserAgent.put(line, count == null ? 1 : count + 1);
				total++;
			}
		}
		catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		List<Map.Entry<String, Long>> entries = new ArrayList(countByUserAgent.entrySet());
		Collections.sort(entries, (o1, o2) -> -o1.getValue().compareTo(o2.getValue()));
		long accumulated = 0;
		for (Map.Entry<String, Long> entry : entries) {
			String userAgent = entry.getKey();
			Long count = entry.getValue();
			accumulated += count;
			System.out.printf(
					"%d (%.1f%%, %.1f%%): %s%n",
					count, count * 100d / total, accumulated * 100d / total, userAgent);
		}
	}
	
}
