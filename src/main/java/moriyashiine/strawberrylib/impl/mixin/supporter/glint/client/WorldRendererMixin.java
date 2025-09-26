/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.Handle;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Inject(method = "method_62214", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw(Lnet/minecraft/client/render/RenderLayer;)V", ordinal = 16, shift = At.Shift.AFTER))
	private void slib$supporterGlint(GpuBufferSlice gpuBufferSlice, WorldRenderState worldRenderState, Profiler profiler, Matrix4f matrix4f, Handle<?> handle, Handle<?> handle2, boolean bl, Frustum frustum, Handle<?> handle3, Handle<?> handle4, CallbackInfo ci, @Local(ordinal = 0) VertexConsumerProvider.Immediate immediate) {
		for (GlintColor color : GlintColor.values()) {
			if (color != GlintColor.PURPLE) {
				GlintLayers layers = GlintLayers.getLayers(color);
				immediate.draw(layers.glintTranslucent());
				immediate.draw(layers.glint());
				immediate.draw(layers.entityGlint());
				immediate.draw(layers.armorEntityGlint());
			}
		}
	}
}
