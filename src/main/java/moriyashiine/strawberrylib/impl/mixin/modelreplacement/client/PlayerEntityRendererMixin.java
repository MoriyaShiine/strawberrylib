/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.client.StrawberrylibClient;
import moriyashiine.strawberrylib.impl.client.supporter.render.entity.state.ModelReplacementRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin<AvatarlikeEntity extends PlayerLikeEntity & ClientPlayerLikeEntity> {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void slib$modelReplacement(AvatarlikeEntity entity, PlayerEntityRenderState state, float tickProgress, CallbackInfo ci) {
		ModelReplacementRenderState modelReplacementRenderState = new ModelReplacementRenderState();
		if (entity instanceof PlayerEntity player && SLibUtils.getModelReplacement(player) instanceof LivingEntity replacement) {
			StrawberrylibClient.currentPlayerRenderState = state;
			modelReplacementRenderState.replacementRenderState = (LivingEntityRenderState) MinecraftClient.getInstance().getEntityRenderDispatcher().getAndUpdateRenderState(replacement, tickProgress);
			StrawberrylibClient.currentPlayerRenderState = null;
		}
		state.setData(ModelReplacementRenderState.KEY, modelReplacementRenderState);
	}
}
