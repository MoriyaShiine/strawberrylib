/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.renderer.item.GlintLayersRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin {
	@ModifyArg(method = "submitItem", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> E slib$supporterGlint(E e) {
		if (e instanceof GlintLayersRenderState.Submit submit) {
			submit.slib$setGlintLayers(GlintLayers.currentLayer);
		}
		return e;
	}

	@ModifyArg(method = "submitModelPart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/ModelPartFeatureRenderer$Storage;add(Lnet/minecraft/client/renderer/rendertype/RenderType;Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;)V"), index = 1)
	private SubmitNodeStorage.ModelPartSubmit slib$supporterGlint(SubmitNodeStorage.ModelPartSubmit submit) {
		((GlintLayersRenderState.Submit) (Object) submit).slib$setGlintLayers(GlintLayers.currentLayer);
		return submit;
	}

	@ModifyVariable(method = "submitModel", at = @At("HEAD"), argsOnly = true)
	private <S> RenderType slib$supporterGlint(RenderType renderType, @Local(argsOnly = true) S state) {
		@Nullable FabricRenderState possibleState = null;
		if (state instanceof FabricRenderState renderState) {
			possibleState = renderState;
		}
		// armor renderer api
		if (state instanceof Pair<?, ?> pair && pair.getSecond() instanceof FabricRenderState renderState) {
			possibleState = renderState;
		}
		if (possibleState != null) {
			@Nullable GlintLayersRenderState glintLayersRenderState = possibleState.getData(GlintLayersRenderState.KEY);
			if (glintLayersRenderState != null && glintLayersRenderState.glintLayers != null) {
				if (renderType == RenderTypes.armorEntityGlint()) {
					return glintLayersRenderState.glintLayers.armorEntityGlint();
				} else if (renderType == RenderTypes.glintTranslucent()) {
					return glintLayersRenderState.glintLayers.glintTranslucent();
				} else if (renderType == RenderTypes.glint()) {
					return glintLayersRenderState.glintLayers.glint();
				} else if (renderType == RenderTypes.entityGlint()) {
					return glintLayersRenderState.glintLayers.entityGlint();
				}
			}
		}
		return renderType;
	}
}
