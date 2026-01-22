/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.client.supporter.renderer.item;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class GlintLayersRenderState {
	public static final RenderStateDataKey<GlintLayersRenderState> KEY = RenderStateDataKey.create(() -> "glint layers");

	public GlintLayers glintLayers = null;

	public interface Submit {
		GlintLayers slib$getGlintLayers();

		void slib$setGlintLayers(GlintLayers glintLayers);
	}
}
