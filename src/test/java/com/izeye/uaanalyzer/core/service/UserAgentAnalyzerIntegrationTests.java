package com.izeye.uaanalyzer.core.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Integration tests for UserAgentAnalyzer.
 *
 * @author Johnny Lim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserAgentAnalyzerIntegrationTests {

	@Autowired
	UserAgentAnalyzer userAgentAnalyzer;

	@Test
	public void testAnalyzeWithFile() {
//		String filename = "src/test/resources/user_agent_pc.txt";
		String filename = "src/test/resources/user_agent_mobile.txt";
//		String filename = "src/test/resources/user_agent_pc.1000.txt";
//		String filename = "src/test/resources/user_agent_mobile.1000.txt";
		this.userAgentAnalyzer.analyze(new File(filename));
	}

}
