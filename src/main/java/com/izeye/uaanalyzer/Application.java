package com.izeye.uaanalyzer;

import com.izeye.uaanalyzer.core.service.UserAgentAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * Created by izeye on 16. 7. 22..
 */
@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private UserAgentAnalyzer userAgentAnalyzer;
	
	@Override
	public void run(String... args) throws Exception {
		this.userAgentAnalyzer.analyze(new File(args[0]));
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
