/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFeatureRenderer.class)
public class ItemFeatureRendererMixin {
	@Inject(method = "prepareFoilSubmit", at = @At("HEAD"))
	private void slib$supporterGlint(ItemFeatureRenderer.Submit submit, CallbackInfo ci) {
		GlintLayers.currentLayer = ((GlintLayersRenderState.Submit) (Object) submit).slib$getGlintLayers();
	}

	@Inject(method = "prepareFoilSubmit", at = @At("TAIL"))
	private void slib$supporterGlint(CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}
}
