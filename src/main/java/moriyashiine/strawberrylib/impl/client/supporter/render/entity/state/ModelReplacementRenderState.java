/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.render.entity.state;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class ModelReplacementRenderState {
	public static final RenderStateDataKey<ModelReplacementRenderState> KEY = RenderStateDataKey.create(() -> "model replacement");

	public LivingEntityRenderState replacementRenderState = null;
}
