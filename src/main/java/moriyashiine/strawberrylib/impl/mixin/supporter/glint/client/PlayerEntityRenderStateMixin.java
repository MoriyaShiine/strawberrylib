/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersAddition;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements GlintLayersAddition {
	@Unique
	private GlintLayers glintLayers = null;

	@Override
	public GlintLayers slib$getGlintLayers() {
		return glintLayers;
	}

	@Override
	public void slib$setGlintLayers(GlintLayers layers) {
		glintLayers = layers;
	}
}
