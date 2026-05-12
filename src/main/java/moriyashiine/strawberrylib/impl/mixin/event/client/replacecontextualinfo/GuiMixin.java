/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.replacecontextualinfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import moriyashiine.strawberrylib.api.event.client.ReplaceContextualInfoEvent;
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
	private void slib$replaceContextualInfo(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		disable = minecraft.player != null && ReplaceContextualInfoEvent.EVENT.invoker().getInfo(minecraft.player) != null;
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;nextContextualInfoState()Lnet/minecraft/client/gui/Gui$ContextualInfo;"))
	private Gui.ContextualInfo slib$replaceContextualInfo(Gui.ContextualInfo original) {
		return disable ? Gui.ContextualInfo.EMPTY : original;
	}

	@ModifyExpressionValue(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"))
	private boolean slib$replaceContextualInfo(boolean original) {
		return original && !disable;
	}
}
