/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(Options.class)
public class OptionsMixin {
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;resetMapping()V"))
	private void slib$supporter(CallbackInfo ci, @Local(ordinal = 1) CompoundTag options) {
		ClientSupporterInit.OPTIONS.values().forEach(data -> data.read(options.getStringOr(data.optionKey(), data.initialName())));
	}

	@Inject(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void slib$supporter(CallbackInfo ci, @Local PrintWriter writer) {
		ClientSupporterInit.OPTIONS.values().forEach(data -> write(writer, data.optionKey(), data.write()));
	}

	@Unique
	private static void write(PrintWriter writer, String key, Object value) {
		writer.println(key + ":" + value);
	}
}
