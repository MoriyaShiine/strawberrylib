/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(OrderedRenderCommandQueueImpl.ItemCommand.class)
public class ItemCommandMixin implements GlintLayersRenderState.Command {
	@Unique
	private GlintLayers glintLayers;

	@Override
	public GlintLayers slib$getGlintLayers() {
		return glintLayers;
	}

	@Override
	public void slib$setGlintLayers(GlintLayers glintLayers) {
		this.glintLayers = glintLayers;
	}
}
