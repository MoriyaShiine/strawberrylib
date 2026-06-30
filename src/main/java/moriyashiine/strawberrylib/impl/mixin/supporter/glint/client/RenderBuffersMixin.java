/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintColor;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin {
	@Shadow
	private static void put(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, RenderType type) {
	}

	@Inject(method = "lambda$new$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;put(Lit/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap;Lnet/minecraft/client/renderer/rendertype/RenderType;)V", ordinal = 2))
	private static void slib$supporterGlint(Object2ObjectLinkedOpenHashMap<RenderType, ByteBufferBuilder> map, CallbackInfo ci) {
		for (GlintColor color : GlintColor.values()) {
			if (color != GlintColor.PURPLE) {
				GlintLayers glintLayers = GlintLayers.getLayers(color);
				put(map, glintLayers.armorEntityGlint());
				put(map, glintLayers.glint());
				put(map, glintLayers.glintTranslucent());
				put(map, glintLayers.entityGlint());
			}
		}
	}
}
