package com.izeye.uaanalyzer.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izeye.uaanalyzer.core.domain.UserAgent;
import com.izeye.uaanalyzer.core.service.UserAgentAnalyzer;

/**
 * Controller for APIs.
 *
 * @author Johnny Lim
 */
@RestController
@RequestMapping(path = "/api/v1")
public class ApiController {

	@Autowired
	private UserAgentAnalyzer userAgentAnalyzer;

	@GetMapping("/analyze")
	public UserAgent analyze(@RequestParam String userAgent) {
		UserAgent analyzed = this.userAgentAnalyzer.analyze(userAgent);
		return analyzed == UserAgent.NOT_AVAILABLE ? null : analyzed;
	}

}
