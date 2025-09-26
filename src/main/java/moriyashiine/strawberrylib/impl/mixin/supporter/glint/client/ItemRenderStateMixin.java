/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersAddition;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements GlintLayersAddition {
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

	@Inject(method = "render", at = @At("HEAD"))
	private void slib$supporterGlint(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, int overlay, int i, CallbackInfo ci) {
		GlintLayers.currentLayer = glintLayers;
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void slib$supporterGlintTail(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, int overlay, int i, CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}
}
