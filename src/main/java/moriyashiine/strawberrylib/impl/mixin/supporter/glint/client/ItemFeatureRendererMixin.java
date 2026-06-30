/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFeatureRenderer.class)
public class ItemFeatureRendererMixin {
	@Inject(method = "renderItem", at = @At("HEAD"))
	private void slib$supporterGlint(MultiBufferSource.BufferSource bufferSource, OutlineBufferSource outlineBufferSource, SubmitNodeStorage.ItemSubmit submit, CallbackInfo ci) {
		GlintLayers.currentLayer = ((GlintLayersRenderState.Submit) (Object) submit).slib$getGlintLayers();
	}

	@Inject(method = "renderItem", at = @At("TAIL"))
	private void slib$supporterGlint(CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}
}
