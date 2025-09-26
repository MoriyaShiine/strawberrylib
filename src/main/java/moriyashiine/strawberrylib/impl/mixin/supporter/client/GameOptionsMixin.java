/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.SupporterOptions;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Unique
	private static final String EQUIPPABLE_GLINT_COLOR_KEY = StrawberryLib.MOD_ID + ".equippableGlintColor";
	@Unique
	private static final String GLINT_COLOR_KEY = StrawberryLib.MOD_ID + ".glintColor";

	@Shadow
	protected MinecraftClient client;

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;updateKeysByCode()V"))
	private void slib$supporterGlint(CallbackInfo ci, @Local(ordinal = 1) NbtCompound compound) {
		if (SupporterInit.isSupporter(client.getGameProfile().id())) {
			SupporterOptions.EQUIPPABLE_GLINT_COLOR.setValue(GlintColor.valueOf(compound.getString(EQUIPPABLE_GLINT_COLOR_KEY, GlintColor.PURPLE.name())));
			SupporterOptions.GLINT_COLOR.setValue(GlintColor.valueOf(compound.getString(GLINT_COLOR_KEY, GlintColor.PURPLE.name())));
		}
	}

	@Inject(method = "write", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void slib$supporterGlint(CallbackInfo ci, @Local PrintWriter printWriter) {
		if (SupporterInit.isSupporter(client.getGameProfile().id())) {
			write(printWriter, EQUIPPABLE_GLINT_COLOR_KEY, SupporterOptions.EQUIPPABLE_GLINT_COLOR.getValue().name());
			write(printWriter, GLINT_COLOR_KEY, SupporterOptions.GLINT_COLOR.getValue().name());
		}
	}

	@Unique
	private static void write(PrintWriter printWriter, String key, Object value) {
		printWriter.println(key + ":" + value);
	}
}
