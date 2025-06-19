/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.ItemRenderStateAddition;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderState.class)
public class ItemRenderStateMixin implements ItemRenderStateAddition {
	@Unique
	private GlintLayers glintLayers = null;

	@Override
	public void slib$setGlintLayers(GlintLayers layers) {
		glintLayers = layers;
	}

	@Inject(method = "render", at = @At("HEAD"))
	private void slib$supporterGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
		if (glintLayers != null) {
			GlintLayers.currentLayer = glintLayers;
		}
	}
}
