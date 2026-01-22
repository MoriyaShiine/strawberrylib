/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.modelreplacement.client;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.client.StrawberrylibClient;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.entity.state.ModelReplacementRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void slib$modelReplacement(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		ModelReplacementRenderState modelReplacementRenderState = new ModelReplacementRenderState();
		if (entity instanceof Player player && SLibUtils.getModelReplacement(player) instanceof LivingEntity replacement) {
			StrawberrylibClient.currentAvatarRenderState = state;
			modelReplacementRenderState.replacementRenderState = (LivingEntityRenderState) Minecraft.getInstance().getEntityRenderDispatcher().extractEntity(replacement, partialTicks);
			StrawberrylibClient.currentAvatarRenderState = null;
		}
		state.setData(ModelReplacementRenderState.KEY, modelReplacementRenderState);
	}
}
