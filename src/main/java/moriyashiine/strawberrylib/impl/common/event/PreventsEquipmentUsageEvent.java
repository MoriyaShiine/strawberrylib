/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.event;

import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class PreventsEquipmentUsageEvent implements TickEntityEvent {
	@Override
	public void tick(ServerWorld world, Entity entity) {
		if (entity instanceof LivingEntity living) {
			for (EquipmentSlot slot : EquipmentSlot.values()) {
				ItemStack stack = living.getEquippedStack(slot);
				if (!stack.isEmpty() && PreventEquipmentUsageEvent.EVENT.invoker().preventsUsage(living, stack)) {
					insertOrDrop(world, entity, stack.copyAndEmpty());
				}
			}
		}
	}

	private static void insertOrDrop(ServerWorld world, Entity entity, ItemStack stack) {
		if (entity instanceof PlayerEntity player) {
			int emptySlot = findEmptySlot(player.getInventory());
			if (emptySlot != -1 && player.getInventory().insertStack(emptySlot, stack)) {
				return;
			}
		}
		entity.dropStack(world, stack);
	}

	private static int findEmptySlot(PlayerInventory inventory) {
		for (int i = 9; i < inventory.getMainStacks().size(); i++) {
			if (inventory.getMainStacks().get(i).isEmpty()) {
				return i;
			}
		}
		return -1;
	}
}
