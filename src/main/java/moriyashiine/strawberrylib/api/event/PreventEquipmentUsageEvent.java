/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event;

import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface PreventEquipmentUsageEvent {
	Event<PreventEquipmentUsageEvent> EVENT = EventFactory.createArrayBacked(PreventEquipmentUsageEvent.class, events -> (entity, stack) -> {
		List<PreventEquipmentUsageEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(PreventEquipmentUsageEvent::getPriority));
		for (PreventEquipmentUsageEvent event : sortedEvents) {
			TriState state = event.preventsUsage(entity, stack);
			if (state != TriState.DEFAULT) {
				return state;
			}
		}
		return TriState.DEFAULT;
	});

	default int getPriority() {
		return 1000;
	}

	TriState preventsUsage(LivingEntity entity, ItemStack stack);

	static void triggerEquipmentCheck(LivingEntity entity) {
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getEquippedStack(slot);
			if (!stack.isEmpty() && PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(entity, stack).get()) {
				SLibUtils.insertOrDrop((ServerWorld) entity.getWorld(), entity, stack.copyAndEmpty());
			}
		}
	}
}
