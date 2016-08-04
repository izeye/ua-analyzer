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

package com.izeye.uaanalyzer;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.izeye.uaanalyzer.core.service.UserAgentAnalyzer;

/**
 * Bootstrap class.
 *
 * @author Johnny Lim
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private UserAgentAnalyzer userAgentAnalyzer;

	@Override
	public void run(String... args) throws Exception {
		if (args.length != 1) {
			// Run as a web service.
			return;
		}

		this.userAgentAnalyzer.analyze(new File(args[0]));
		System.exit(0);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
