/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;updateKeysByCode()V"))
	private void slib$supporter(CallbackInfo ci, @Local(ordinal = 1) NbtCompound compound) {
		ClientSupporterInit.OPTIONS.forEach((key, data) -> data.read(compound.getString(data.optionKey(), data.defaultName())));
	}

	@Inject(method = "write", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void slib$supporter(CallbackInfo ci, @Local PrintWriter printWriter) {
		ClientSupporterInit.OPTIONS.forEach((key, data) -> write(printWriter, data.optionKey(), data.write()));
	}

	@Unique
	private static void write(PrintWriter printWriter, String key, Object value) {
		printWriter.println(key + ":" + value);
	}
}
