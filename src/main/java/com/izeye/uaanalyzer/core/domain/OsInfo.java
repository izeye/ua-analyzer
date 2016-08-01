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

}
