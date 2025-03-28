/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilderStorage.class)
public abstract class BufferedBuilderStorageMixin {
	@Shadow
	private static void assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> builderStorage, RenderLayer layer) {
	}

	@Inject(method = "method_54639", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BufferBuilderStorage;assignBufferBuilder(Lit/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap;Lnet/minecraft/client/render/RenderLayer;)V", ordinal = 0))
	private void slib$supporterGlint(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map, CallbackInfo ci) {
		for (GlintColor color : GlintColor.values()) {
			if (color != GlintColor.PURPLE) {
				GlintLayers layers = GlintLayers.getLayers(color);
				assignBufferBuilder(map, layers.glintTranslucent());
				assignBufferBuilder(map, layers.glint());
				assignBufferBuilder(map, layers.entityGlint());
				assignBufferBuilder(map, layers.armorEntityGlint());
			}
		}
	}
}
