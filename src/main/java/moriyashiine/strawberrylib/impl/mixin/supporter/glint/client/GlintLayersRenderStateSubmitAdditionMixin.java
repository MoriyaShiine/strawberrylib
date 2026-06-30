/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.minecraft.client.renderer.SubmitNodeStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({SubmitNodeStorage.ItemSubmit.class, SubmitNodeStorage.ModelPartSubmit.class})
public class GlintLayersRenderStateSubmitAdditionMixin implements GlintLayersRenderState.Submit {
	@Unique
	private GlintLayers glintLayers;

	@Override
	public GlintLayers slib$getGlintLayers() {
		return glintLayers;
	}

	@Override
	public void slib$setGlintLayers(GlintLayers glintLayers) {
		this.glintLayers = glintLayers;
	}
}
