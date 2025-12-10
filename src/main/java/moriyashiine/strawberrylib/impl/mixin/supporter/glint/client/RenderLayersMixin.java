/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {
	@WrapMethod(method = "armorEntityGlint")
	private static RenderLayer slib$supporterGlintArmorEntity(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.armorEntityGlint();
		}
		return original.call();
	}

	@WrapMethod(method = "glintTranslucent")
	private static RenderLayer slib$supporterGlintTranslucent(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glintTranslucent();
		}
		return original.call();
	}

	@WrapMethod(method = "glint")
	private static RenderLayer slib$supporterGlint(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glint();
		}
		return original.call();
	}

	@WrapMethod(method = "entityGlint")
	private static RenderLayer slib$supporterGlintEntity(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.entityGlint();
		}
		return original.call();
	}
}
