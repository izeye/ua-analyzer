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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Operating system information.
 *
 * @author Johnny Lim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OsInfo implements Comparable<OsInfo> {

	/**
	 * OsInfo instance indicating that resolution has been failed.
	 */
	public static final OsInfo NOT_AVAILABLE = new OsInfo();

	private OsType osType;
	private String osVersion;
	private String description;

	public OsInfo(OsType osType) {
		this(osType, null, null);
	}

	public OsInfo(OsType osType, String osVersion) {
		this(osType, osVersion, null);
	}

	@Override
	public int compareTo(OsInfo o) {
		int result = ObjectUtils.compare(osType, o.osType);
		if (result != 0) {
			return result;
		}
		result = ObjectUtils.compare(osVersion, o.osVersion);
		if (result != 0) {
			return result;
		}
		return ObjectUtils.compare(description, o.description);
	}

	public String toPrettyString() {
		if (osType == null) {
			return "N/A";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(osType.getName());
		if (osVersion != null) {
			sb.append(" ");
			sb.append(osVersion);
		}
		if (description != null) {
			sb.append(" (");
			sb.append(description);
			sb.append(")");
		}
		return sb.toString();
	}

}
