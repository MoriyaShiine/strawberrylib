/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownTridentRenderer.class)
public class ThrownTridentRendererMixin {
	@ModifyArg(method = "submit(Lnet/minecraft/client/renderer/entity/state/ThrownTridentRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"), index = 3)
	private RenderType slib$supporterGlint(RenderType renderType, @Local(argsOnly = true) ThrownTridentRenderState state) {
		GlintLayersRenderState glintLayersRenderState = state.getData(GlintLayersRenderState.KEY);
		if (glintLayersRenderState != null && glintLayersRenderState.glintLayers != null) {
			return glintLayersRenderState.glintLayers.entityGlint();
		}
		return renderType;
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/projectile/arrow/ThrownTrident;Lnet/minecraft/client/renderer/entity/state/ThrownTridentRenderState;F)V", at = @At("TAIL"))
	private void slib$supporterGlint(ThrownTrident entity, ThrownTridentRenderState state, float partialTicks, CallbackInfo ci) {
		GlintLayersRenderState glintLayersRenderState = new GlintLayersRenderState();
		if (entity.getOwner() instanceof Player player && SLibSupporterUtils.isSupporter(player)) {
			glintLayersRenderState.glintLayers = GlintLayers.getLayers(SLibSupporterUtils.getData(player, SupporterInit.GLINT_COLOR));
		}
		state.setData(GlintLayersRenderState.KEY, glintLayersRenderState);
	}
}
