/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.disablecontextualinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.DisableContextualInfoEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
	@Unique
	private boolean disable = false;

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"))
	private void slib$disableContextualInfo(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		disable = minecraft.player != null && DisableContextualInfoEvent.EVENT.invoker().shouldDisable(minecraft.player).get();
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;nextContextualInfoState()Lnet/minecraft/client/gui/Gui$ContextualInfo;"))
	private Gui.ContextualInfo slib$disableContextualInfo(Gui.ContextualInfo original) {
		return disable ? Gui.ContextualInfo.EMPTY : original;
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"))
	private boolean slib$disableContextualInfo(boolean original) {
		return original && !disable;
	}
}
