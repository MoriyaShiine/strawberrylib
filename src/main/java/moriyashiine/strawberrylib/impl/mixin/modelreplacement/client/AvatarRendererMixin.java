/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.entity.state.ModelReplacementRenderState;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void slib$modelReplacement(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		ModelReplacementRenderState modelReplacementRenderState = new ModelReplacementRenderState();
		if (entity instanceof Player player && SLibUtils.getModelReplacement(player) instanceof LivingEntity replacement) {
			LivingEntityRenderState replacementRenderState = (LivingEntityRenderState) Minecraft.getInstance().getEntityRenderDispatcher().extractEntity(replacement, partialTicks);
			replaceRenderStateData(state, replacementRenderState);
			modelReplacementRenderState.replacementRenderState = replacementRenderState;
			modelReplacementRenderState.hasId = replacement.getId() != ModelReplacementComponent.AWAITING_ID;
		}
		state.setData(ModelReplacementRenderState.KEY, modelReplacementRenderState);
	}

	@Unique
	private static void replaceRenderStateData(AvatarRenderState state, LivingEntityRenderState replacementRenderState) {
		// Entity
		replacementRenderState.x = state.x;
		replacementRenderState.y = state.y;
		replacementRenderState.z = state.z;
		replacementRenderState.distanceToCameraSq = state.distanceToCameraSq;
		// LivingEntity
		replacementRenderState.bodyRot = state.bodyRot;
		replacementRenderState.yRot = state.yRot;
		replacementRenderState.xRot = state.xRot;
		// Armed
		if (replacementRenderState instanceof ArmedEntityRenderState armedEntityRenderState) {
			armedEntityRenderState.attackTime = state.attackTime;
		}
	}
}
