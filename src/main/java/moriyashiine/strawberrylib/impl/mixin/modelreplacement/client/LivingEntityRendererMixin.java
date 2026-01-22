/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static moriyashiine.strawberrylib.impl.client.StrawberrylibClient.currentAvatarRenderState;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
	private void slib$modelReplacement(T entity, S state, float partialTicks, CallbackInfo ci) {
		if (currentAvatarRenderState != null) {
			// Entity
			state.x = currentAvatarRenderState.x;
			state.y = currentAvatarRenderState.y;
			state.z = currentAvatarRenderState.z;
			state.distanceToCameraSq = currentAvatarRenderState.distanceToCameraSq;
			// LivingEntity
			state.bodyRot = currentAvatarRenderState.bodyRot;
			state.yRot = currentAvatarRenderState.yRot;
			state.xRot = currentAvatarRenderState.xRot;
		}
	}
}
