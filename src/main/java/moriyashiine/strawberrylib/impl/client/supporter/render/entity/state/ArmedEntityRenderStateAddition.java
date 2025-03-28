/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.render.entity.state;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import net.minecraft.util.Arm;

public interface ArmedEntityRenderStateAddition {
	GlintLayers slib$getGlintLayers(Arm arm);

	void slib$setGlintLayers(Arm arm, GlintLayers layers);
}
