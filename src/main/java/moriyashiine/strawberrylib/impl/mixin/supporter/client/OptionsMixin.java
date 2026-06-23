/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;
import moriyashiine.strawberrylib.api.supporter.objects.client.ClientSupporterData;
import moriyashiine.strawberrylib.impl.client.supporter.ClientSupporterInit;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mixin(Options.class)
public class OptionsMixin {
	@Unique
	private static final TagParser<Tag> TAG_PARSER = TagParser.create(NbtOps.INSTANCE);

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/KeyMapping;resetMapping()V"))
	private void slib$supporter(CallbackInfo ci, @Local(ordinal = 1) CompoundTag options) {
		List<SupporterDataType<?>> sorted = new ArrayList<>(ClientSupporterInit.OPTIONS.keySet());
		sorted.sort(Comparator.comparing(SupporterDataType::key));
		sorted.forEach(type -> read(type, ClientSupporterInit.OPTIONS.get(type), options));
	}

	@Inject(method = "save", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void slib$supporter(CallbackInfo ci, @Local PrintWriter writer) {
		List<SupporterDataType<?>> sorted = new ArrayList<>(ClientSupporterInit.OPTIONS.keySet());
		sorted.sort(Comparator.comparing(SupporterDataType::key));
		sorted.forEach(type -> write(type, ClientSupporterInit.OPTIONS.get(type), writer));
	}

	@SuppressWarnings("unchecked")
	@Unique
	private static <T> void read(SupporterDataType<T> type, ClientSupporterData<?> clientData, CompoundTag options) {
		T value = options.getString(clientData.optionKey()).flatMap(string -> {
			try {
				return type.codec().parse(NbtOps.INSTANCE, TAG_PARSER.parseFully(string)).result();
			} catch (CommandSyntaxException exception) {
				return Optional.empty();
			}
		}).orElseGet(type::initialValue);
		((OptionInstance<T>) clientData.option()).set(value);
	}

	@SuppressWarnings("unchecked")
	@Unique
	private static <T> void write(SupporterDataType<T> type, ClientSupporterData<?> clientData, PrintWriter writer) {
		type.codec().encodeStart(NbtOps.INSTANCE, (T) clientData.option().get()).ifSuccess(tag -> writer.println(clientData.optionKey() + ":" + tag));
	}
}
