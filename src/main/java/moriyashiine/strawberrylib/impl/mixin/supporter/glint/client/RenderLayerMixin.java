/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {
	@WrapMethod(method = "getGlintTranslucent")
	private static RenderLayer slib$supporterGlintTranslucent(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glintTranslucent();
		}
		return original.call();
	}

	@WrapMethod(method = "getGlint")
	private static RenderLayer slib$supporterGlint(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glint();
		}
		return original.call();
	}

	@WrapMethod(method = "getEntityGlint")
	private static RenderLayer slib$supporterGlintEntity(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.entityGlint();
		}
		return original.call();
	}

	@WrapMethod(method = "getArmorEntityGlint")
	private static RenderLayer slib$supporterGlintArmorEntity(Operation<RenderLayer> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.armorEntityGlint();
		}
		return original.call();
	}
}
