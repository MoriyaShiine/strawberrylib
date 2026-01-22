/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderTypes.class)
public class RenderTypesMixin {
	@WrapMethod(method = "armorEntityGlint")
	private static RenderType slib$supporterGlintArmorEntity(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.armorEntityGlint();
		}
		return original.call();
	}

	@WrapMethod(method = "glintTranslucent")
	private static RenderType slib$supporterGlintTranslucent(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glintTranslucent();
		}
		return original.call();
	}

	@WrapMethod(method = "glint")
	private static RenderType slib$supporterGlint(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.glint();
		}
		return original.call();
	}

	@WrapMethod(method = "entityGlint")
	private static RenderType slib$supporterGlintEntity(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.entityGlint();
		}
		return original.call();
	}
}
