/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.init;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<SupporterComponent> SUPPORTER = ComponentRegistry.getOrCreate(StrawberryLib.id("supporter"), SupporterComponent.class);
	public static final ComponentKey<ModelReplacementComponent> MODEL_REPLACEMENT = ComponentRegistry.getOrCreate(StrawberryLib.id("model_replacement"), ModelReplacementComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(SUPPORTER, SupporterComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(MODEL_REPLACEMENT, ModelReplacementComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
	}
}
