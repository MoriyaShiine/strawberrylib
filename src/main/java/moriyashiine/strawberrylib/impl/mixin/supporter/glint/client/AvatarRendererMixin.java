/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void slib$supporterGlint(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		GlintLayersRenderState glintLayersRenderState = new GlintLayersRenderState();
		if (entity instanceof Player player && SLibSupporterUtils.isSupporter(player)) {
			glintLayersRenderState.glintLayers = GlintLayers.getLayers(SLibSupporterUtils.getData(player, SupporterInit.EQUIPPABLE_GLINT_COLOR));
		}
		state.setData(GlintLayersRenderState.KEY, glintLayersRenderState);
	}
}
