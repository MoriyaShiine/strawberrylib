/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.render.entity.state;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public interface ModelReplacementAddition {
	LivingEntityRenderState slib$getReplacementRenderState();

	void slib$setReplacementRenderState(LivingEntityRenderState replacementState);
}
