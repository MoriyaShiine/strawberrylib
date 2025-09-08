/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.event.client.disablehudbar;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.DisableHudBarEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Unique
	private boolean shouldDisableHudBar = false;

	@Inject(method = "renderMainHud", at = @At("HEAD"))
	private void slib$disableHudBar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		PlayerEntity player = MinecraftClient.getInstance().player;
		shouldDisableHudBar = player != null && DisableHudBarEvent.EVENT.invoker().shouldDisableHudBar(player).get();
	}

	@ModifyExpressionValue(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;getCurrentBarType()Lnet/minecraft/client/gui/hud/InGameHud$BarType;"))
	private InGameHud.BarType slib$disableHudBar(InGameHud.BarType original) {
		return shouldDisableHudBar ? InGameHud.BarType.EMPTY : original;
	}

	@ModifyExpressionValue(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasExperienceBar()Z"))
	private boolean slib$disableHudBar(boolean original) {
		return original && !shouldDisableHudBar;
	}
}
