/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.init;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import moriyashiine.strawberrylib.impl.common.component.entity.ModelReplacementComponent;
import moriyashiine.strawberrylib.impl.common.component.entity.StoredEquipmentComponent;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModEntityComponents implements EntityComponentInitializer {
	public static final ComponentKey<SupporterComponent> SUPPORTER = ComponentRegistry.getOrCreate(StrawberryLib.id("supporter"), SupporterComponent.class);
	public static final ComponentKey<ModelReplacementComponent> MODEL_REPLACEMENT = ComponentRegistry.getOrCreate(StrawberryLib.id("model_replacement"), ModelReplacementComponent.class);
	public static final ComponentKey<StoredEquipmentComponent> STORED_EQUIPMENT = ComponentRegistry.getOrCreate(StrawberryLib.id("stored_equipment"), StoredEquipmentComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(SUPPORTER, SupporterComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
		registry.registerForPlayers(MODEL_REPLACEMENT, ModelReplacementComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
		registry.beginRegistration(LivingEntity.class, STORED_EQUIPMENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(StoredEquipmentComponent::new);
	}
}
