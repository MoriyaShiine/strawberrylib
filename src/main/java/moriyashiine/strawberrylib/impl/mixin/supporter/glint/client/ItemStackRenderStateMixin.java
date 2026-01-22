/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderState.class)
public class ItemStackRenderStateMixin {
	@Inject(method = "submit", at = @At("HEAD"))
	private void slib$supporterGlint(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor, CallbackInfo ci) {
		GlintLayers glintLayers = null;
		@Nullable GlintLayersRenderState glintLayersRenderState = ((FabricRenderState) this).getData(GlintLayersRenderState.KEY);
		if (glintLayersRenderState != null) {
			glintLayers = glintLayersRenderState.glintLayers;
		}
		GlintLayers.currentLayer = glintLayers;
	}

	@Inject(method = "submit", at = @At("TAIL"))
	private void slib$supporterGlint(CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}
}
