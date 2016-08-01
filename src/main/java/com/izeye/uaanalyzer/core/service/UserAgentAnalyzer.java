package com.izeye.uaanalyzer.core.service;

import com.izeye.uaanalyzer.core.domain.UserAgent;

import java.io.File;

/**
 * Created by izeye on 16. 7. 22..
 */
public interface UserAgentAnalyzer {
	
	UserAgent analyze(String userAgentString);
	
	void analyze(File userAgentListFile);
	
}
