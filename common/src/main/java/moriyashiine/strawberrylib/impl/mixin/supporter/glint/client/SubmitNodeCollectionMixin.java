package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.feature.submit.SubmitNode;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin {
	@ModifyArg(method = "submitItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/phase/FeatureRenderPhase;submit(Lnet/minecraft/client/renderer/feature/submit/SubmitNode;)V"))
	private SubmitNode slib$supporterGlintTranslucent(SubmitNode submit) {
		((GlintLayersRenderState.Submit) submit).slib$setGlintLayers(GlintLayers.currentLayer);
		return submit;
	}

	@ModifyArg(method = "submitItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/phase/SimpleFeatureRenderPhase;submit(Lnet/minecraft/client/renderer/feature/submit/SubmitNode;)V", ordinal = 0))
	private SubmitNode slib$supporterGlint(SubmitNode submit) {
		((GlintLayersRenderState.Submit) submit).slib$setGlintLayers(GlintLayers.currentLayer);
		return submit;
	}

	@ModifyVariable(method = "submitModel", at = @At("HEAD"), argsOnly = true)
	private <S> RenderType slib$supporterGlint(RenderType renderType, @Local(argsOnly = true) S state) {
		FabricRenderState possibleState = null;
		if (state instanceof FabricRenderState renderState) {
			possibleState = renderState;
		}
		// armor renderer api
		if (state instanceof Pair<?, ?> pair && pair.getSecond() instanceof FabricRenderState renderState) {
			possibleState = renderState;
		}
		if (possibleState != null) {
			GlintLayersRenderState glintLayersRenderState = possibleState.getData(GlintLayersRenderState.KEY);
			if (glintLayersRenderState != null && glintLayersRenderState.glintLayers != null) {
				if (renderType.name.equals("armor_cutout_no_cull_glint")) {
					return glintLayersRenderState.glintLayers.armorCutoutNoCullGlint().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType.name.equals("entity_solid_glint")) {
					return glintLayersRenderState.glintLayers.entitySolidGlint().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType.name.equals("item_cutout_glint")) {
					return glintLayersRenderState.glintLayers.itemCutoutGlint().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType.name.equals("item_cutout_glint_special")) {
					return glintLayersRenderState.glintLayers.itemCutoutGlintSpecial().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType.name.equals("item_translucent_glint")) {
					return glintLayersRenderState.glintLayers.itemTranslucentGlint().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType.name.equals("item_translucent_glint_special")) {
					return glintLayersRenderState.glintLayers.itemTranslucentGlintSpecial().apply(renderType.state.textures.get("Sampler0").location());
				} else if (renderType == RenderTypes.patternedShieldGlint()) {
					return glintLayersRenderState.glintLayers.patternedShieldGlint();
				} else if (renderType == RenderTypes.trimmedArmorGlint()) {
					return glintLayersRenderState.glintLayers.trimmedArmorGlint();
				}
			}
		}
		return renderType;
	}
}
