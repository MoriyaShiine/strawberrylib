package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Function;

@Mixin(ItemFeatureRenderer.class)
public class ItemFeatureRendererMixin {
	@WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
	private RenderType slib$supporterGlint(BakedQuad.MaterialInfo instance, Operation<RenderType> original, ItemFeatureRenderer.Submit submit) {
		RenderType type = original.call(instance);
		GlintLayers glintLayers = ((GlintLayersRenderState.Submit) (Object) submit).slib$getGlintLayers();
		if (glintLayers != null) {
			Function<Identifier, RenderType> glintType = type.name.equals("item_cutout_glint") ? glintLayers.itemCutoutGlint() : glintLayers.itemTranslucentGlint();
			return glintType.apply(type.state.textures.get("Sampler0").location());
		}
		return type;
	}

	@WrapOperation(method = "prepareMainSubmit", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/geometry/BakedQuad$MaterialInfo;itemGlintSpecialRenderType()Lnet/minecraft/client/renderer/rendertype/RenderType;"))
	private RenderType slib$supporterGlintSpecial(BakedQuad.MaterialInfo instance, Operation<RenderType> original, ItemFeatureRenderer.Submit submit) {
		RenderType type = original.call(instance);
		GlintLayers glintLayers = ((GlintLayersRenderState.Submit) (Object) submit).slib$getGlintLayers();
		if (glintLayers != null) {
			Function<Identifier, RenderType> glintType = type.name.equals("item_cutout_glint_special") ? glintLayers.itemCutoutGlintSpecial() : glintLayers.itemTranslucentGlintSpecial();
			return glintType.apply(type.state.textures.get("Sampler0").location());
		}
		return type;
	}
}
