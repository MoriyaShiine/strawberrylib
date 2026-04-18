/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.objects.enums;

public enum PreventionResult {
	PASS(false, false),
	PREVENT(true, false),
	PREVENT_AND_STORE(true, true);

	public final boolean prevent, store;

	PreventionResult(boolean prevent, boolean store) {
		this.prevent = prevent;
		this.store = store;
	}
}
