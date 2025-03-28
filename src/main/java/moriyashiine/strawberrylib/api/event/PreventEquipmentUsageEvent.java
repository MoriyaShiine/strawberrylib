/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface PreventEquipmentUsageEvent {
	Event<PreventEquipmentUsageEvent> EVENT = EventFactory.createArrayBacked(PreventEquipmentUsageEvent.class, events -> (entity, stack) -> {
		for (PreventEquipmentUsageEvent event : events) {
			if (event.preventsUsage(entity, stack)) {
				return true;
			}
		}
		return false;
	});

	boolean preventsUsage(LivingEntity entity, ItemStack stack);
}
