/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.objects.enums;

public enum PacketTarget {
	ALL(true, true),
	SELF(true, false),
	OTHERS(false, true);

	private final boolean sendsToSelf, sendsToOthers;

	PacketTarget(boolean sendsToSelf, boolean sendsToOthers) {
		this.sendsToSelf = sendsToSelf;
		this.sendsToOthers = sendsToOthers;
	}

	public boolean sendsToSelf() {
		return sendsToSelf;
	}

	public boolean sendsToOthers() {
		return sendsToOthers;
	}
}
