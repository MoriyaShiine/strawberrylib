/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

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

	static void triggerEquipmentCheck(LivingEntity entity) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getEquippedStack(slot);
			if (!stack.isEmpty() && PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(entity, stack)) {
				SLibUtils.insertOrDrop((ServerWorld) entity.getWorld(), entity, stack.copyAndEmpty());
			}
		}
	}
}
