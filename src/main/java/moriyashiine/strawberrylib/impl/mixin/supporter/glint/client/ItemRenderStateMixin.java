/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void slib$supporterGlint(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, int overlay, int i, CallbackInfo ci) {
		GlintLayers glintLayers = null;
		@Nullable GlintLayersRenderState glintLayersRenderState = ((FabricRenderState) this).getData(GlintLayersRenderState.KEY);
		if (glintLayersRenderState != null) {
			glintLayers = glintLayersRenderState.glintLayers;
		}
		GlintLayers.currentLayer = glintLayers;
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void slib$supporterGlintTail(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, int overlay, int i, CallbackInfo ci) {
		GlintLayers.currentLayer = null;
	}
}
