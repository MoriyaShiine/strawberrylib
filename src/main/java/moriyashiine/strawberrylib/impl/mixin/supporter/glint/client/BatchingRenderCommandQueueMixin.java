/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersRenderState;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.BatchingRenderCommandQueue;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BatchingRenderCommandQueue.class)
public class BatchingRenderCommandQueueMixin {
	@ModifyArg(method = "submitItem", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private <E> E slib$supporterGlint(E e) {
		if (e instanceof GlintLayersRenderState.Command command) {
			command.slib$setGlintLayers(GlintLayers.currentLayer);
		}
		return e;
	}

	@ModifyVariable(method = "submitModel", at = @At("HEAD"), argsOnly = true)
	private <S> RenderLayer slib$supporterGlint(RenderLayer layer, @Local(argsOnly = true) S state) {
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
				if (layer == RenderLayer.getGlintTranslucent()) {
					return glintLayersRenderState.glintLayers.glintTranslucent();
				} else if (layer == RenderLayer.getGlint()) {
					return glintLayersRenderState.glintLayers.glint();
				} else if (layer == RenderLayer.getEntityGlint()) {
					return glintLayersRenderState.glintLayers.entityGlint();
				} else if (layer == RenderLayer.getArmorEntityGlint()) {
					return glintLayersRenderState.glintLayers.armorEntityGlint();
				}
			}
		}
		return layer;
	}
}
