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

package com.izeye.uaanalyzer.core.domain;

/**
 * Browser type.
 *
 * @author Johnny Lim
 */
public enum BrowserType {

	/**
	 * Internet Explorer.
	 */
	IE("Internet Explorer"),

	/**
	 * Edge.
	 */
	EDGE("Edge"),

	/**
	 * Chrome.
	 */
	CHROME("Chrome"),

	/**
	 * Firefox.
	 */
	FIREFOX("Firefox"),

	/**
	 * Safari.
	 */
	SAFARI("Safari"),

	/**
	 * Opera.
	 */
	OPERA("Opera"),

	/**
	 * Iron.
	 */
	IRON("Iron");

	private final String name;

	BrowserType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
