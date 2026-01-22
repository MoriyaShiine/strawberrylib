/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option.SupporterOptionsScreen;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
	@Unique
	private static final Component STRAWBERRYLIB_TEXT = Component.translatable("options." + StrawberryLib.MOD_ID).withStyle(ChatFormatting.WHITE);

	@Shadow
	@Final
	private Options options;

	@Shadow
	protected abstract Button openScreenButton(Component message, Supplier<Screen> screenToScreen);

	protected OptionsScreenMixin(Component title) {
		super(title);
	}

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/HeaderAndFooterLayout;addToContents(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;"))
	private void slib$supporter(CallbackInfo ci, @Local(name = "helper") GridLayout.RowHelper helper) {
		if (SupporterInit.isSupporter(minecraft.getGameProfile().id())) {
			helper.addChild(openScreenButton(SupporterOptionsScreen.STRAWBERRY_TEXT.copy().append(STRAWBERRYLIB_TEXT), () -> new SupporterOptionsScreen(this, options)));
		}
	}
}
