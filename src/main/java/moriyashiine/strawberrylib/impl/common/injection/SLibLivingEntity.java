/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.injection;

public interface SLibLivingEntity {
	default boolean slib$isPlayer() {
		throw new AssertionError("Implemented in Mixin");
	}

	default boolean slib$isSurvival() {
		throw new AssertionError("Implemented in Mixin");
	}

	default boolean slib$exists() {
		throw new AssertionError("Implemented in Mixin");
	}
}
