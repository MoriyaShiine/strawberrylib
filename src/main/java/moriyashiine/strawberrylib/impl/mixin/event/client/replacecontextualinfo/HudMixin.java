/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.replacecontextualinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.ReplaceContextualInfoEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class HudMixin {
	@Unique
	private boolean disable = false;

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"))
	private void slib$replaceContextualInfo(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		disable = minecraft.player != null && ReplaceContextualInfoEvent.EVENT.invoker().getInfo(minecraft.player) != null;
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;nextContextualInfoState()Lnet/minecraft/client/gui/Hud$ContextualInfo;"))
	private Hud.ContextualInfo slib$replaceContextualInfo(Hud.ContextualInfo original) {
		return disable ? Hud.ContextualInfo.EMPTY : original;
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"))
	private boolean slib$replaceContextualInfo(boolean original) {
		return original && !disable;
	}
}
