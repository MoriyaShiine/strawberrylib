/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;

public final class SLib {
	public static void init(String modId) {
		StrawberryLib.currentModId = modId;
	}
}
