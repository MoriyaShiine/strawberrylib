package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RenderTypes.class)
public class RenderTypesMixin {
	@WrapMethod(method = "armorCutoutNoCullGlint")
	private static RenderType slib$supporterGlintArmorCutoutNoCullGlint(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.armorCutoutNoCullGlint().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "entitySolidGlint")
	private static RenderType slib$supporterGlintEntitySolidGlint(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.entitySolidGlint().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "itemCutoutGlint")
	private static RenderType slib$supporterGlintItemCutoutGlint(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.itemCutoutGlint().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "itemCutoutGlintSpecial")
	private static RenderType slib$supporterGlintItemCutoutGlintSpecial(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.itemCutoutGlintSpecial().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "itemTranslucentGlint")
	private static RenderType slib$supporterGlintItemTranslucentGlint(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.itemTranslucentGlint().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "itemTranslucentGlintSpecial")
	private static RenderType slib$supporterGlintItemTranslucentGlintSpecial(Identifier texture, Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.itemTranslucentGlintSpecial().apply(texture);
		}
		return original.call(texture);
	}

	@WrapMethod(method = "patternedShieldGlint")
	private static RenderType slib$supporterGlintPatternedShieldGlint(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.patternedShieldGlint();
		}
		return original.call();
	}

	@WrapMethod(method = "trimmedArmorGlint")
	private static RenderType slib$supporterGlintTrimmedArmorGlint(Operation<RenderType> original) {
		if (GlintLayers.currentLayer != null) {
			return GlintLayers.currentLayer.trimmedArmorGlint();
		}
		return original.call();
	}
}
