package com.izeye.uaanalyzer.core.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by izeye on 16. 7. 22..
 */
public class UserAgentAnalyzerTests {
	
	private UserAgentAnalyzer userAgentAnalyzer;
	
	@Before
	public void setUp() {
		this.userAgentAnalyzer = new DefaultUserAgentAnalyzer();
	}
	
	@Test
	public void testAnalyze() {
		String filename = "src/test/resources/user_agent_pc.1000.txt";
//		String filename = "src/test/resources/user_agent_mobile.1000.txt";
		this.userAgentAnalyzer.analyze(new File(filename));
	}
	
}
