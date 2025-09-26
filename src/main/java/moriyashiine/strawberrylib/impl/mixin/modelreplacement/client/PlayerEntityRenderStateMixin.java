/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.impl.client.supporter.render.entity.state.ModelReplacementAddition;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements ModelReplacementAddition {
	@Unique
	private LivingEntityRenderState replacementState = null;

	@Override
	public LivingEntityRenderState slib$getReplacementRenderState() {
		return replacementState;
	}

	@Override
	public void slib$setReplacementRenderState(LivingEntityRenderState replacementState) {
		this.replacementState = replacementState;
	}
}
