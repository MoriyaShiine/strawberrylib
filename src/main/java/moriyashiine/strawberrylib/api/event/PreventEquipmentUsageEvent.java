/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.event;

import moriyashiine.strawberrylib.api.objects.enums.PreventionResult;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
public interface PreventEquipmentUsageEvent {
	Event<PreventEquipmentUsageEvent> EVENT = EventFactory.createArrayBacked(PreventEquipmentUsageEvent.class, events -> (entity, stack, slot) -> {
		List<PreventEquipmentUsageEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(PreventEquipmentUsageEvent::getPriority));
		for (PreventEquipmentUsageEvent event : sortedEvents) {
			PreventionResult result = event.getPreventionResult(entity, stack, slot);
			if (result != PreventionResult.PASS) {
				return result;
			}
		}
		return PreventionResult.PASS;
	});

	default int getPriority() {
		return 1000;
	}

	PreventionResult getPreventionResult(LivingEntity entity, ItemStack stack, EquipmentSlot slot);

	static boolean cannotEquip(LivingEntity entity, ItemStack stack, EquipmentSlot slot) {
		return ModEntityComponents.STORED_EQUIPMENT.get(entity).hasStoredStack(slot) || PreventEquipmentUsageEvent.EVENT.invoker().getPreventionResult(entity, stack, slot).prevent;
	}
}
