/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option.SupporterOptionsScreen;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
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
	private static final Text STRAWBERRYLIB_TEXT = Text.translatable("options." + StrawberryLib.MOD_ID).formatted(Formatting.WHITE);

	@Shadow
	@Final
	private GameOptions settings;

	@Shadow
	protected abstract ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier);

	protected OptionsScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ThreePartsLayoutWidget;addBody(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;"))
	private void slib$supporterGlint(CallbackInfo ci, @Local GridWidget.Adder adder) {
		if (SupporterInit.isSupporter(client.getGameProfile().getId())) {
			adder.add(createButton(SupporterOptionsScreen.STRAWBERRY_TEXT.copy().append(STRAWBERRYLIB_TEXT), () -> new SupporterOptionsScreen(this, settings)));
		}
	}
}
