/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.common.component.entity;

import moriyashiine.strawberrylib.api.event.PreventEquipmentUsageEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class StoredEquipmentComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity obj;
	private final EntityEquipment storedEquipment = new EntityEquipment();
	private int hotbarIndex = -1;

	public StoredEquipmentComponent(LivingEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ValueInput input) {
		storedEquipment.setAll(input.read("StoredEquipment", EntityEquipment.CODEC).orElseGet(EntityEquipment::new));
		hotbarIndex = input.getIntOr("HotbarIndex", -1);
	}

	@Override
	public void writeData(ValueOutput output) {
		if (!storedEquipment.isEmpty()) {
			output.store("StoredEquipment", EntityEquipment.CODEC, storedEquipment);
		}
		output.putInt("HotbarIndex", hotbarIndex);
	}

	@Override
	public void serverTick() {
		boolean sync = false;
		PreventEquipmentUsageEvent invoker = PreventEquipmentUsageEvent.EVENT.invoker();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack equippedStack = obj.getItemBySlot(slot);
			ItemStack storedStack = storedEquipment.get(slot);
			if (invoker.getPreventionResult(obj, equippedStack.isEmpty() ? storedStack : equippedStack, slot).prevent) {
				if (!equippedStack.isEmpty()) {
					if (invoker.getPreventionResult(obj, equippedStack, slot).store) {
						sync |= tryInsert(slot);
					} else {
						SLibUtils.insertOrDrop((ServerLevel) obj.level(), obj, equippedStack.copyAndClear());
					}
				}
			} else if (slot != EquipmentSlot.MAINHAND) {
				sync |= extract(storedStack, slot);
			}
		}
		ItemStack storedMainHandStack = storedEquipment.get(EquipmentSlot.MAINHAND);
		if ((obj instanceof Player player && player.getInventory().getSelectedSlot() != hotbarIndex) || !invoker.getPreventionResult(obj, storedMainHandStack, EquipmentSlot.MAINHAND).prevent) {
			sync |= extract(storedMainHandStack, EquipmentSlot.MAINHAND);
		}
		if (sync) {
			sync();
		}
	}

	public void sync() {
		ModEntityComponents.STORED_EQUIPMENT.sync(obj);
	}

	public int getHotbarIndex() {
		return hotbarIndex;
	}

	public ItemStack getStoredStack(EquipmentSlot slot) {
		return storedEquipment.get(slot);
	}

	public boolean hasStoredStack(EquipmentSlot slot) {
		return !storedEquipment.get(slot).isEmpty();
	}

	public void dropStoredEquipment() {
		storedEquipment.dropAll(obj);
		hotbarIndex = -1;
	}

	private boolean extract(ItemStack stack, EquipmentSlot slot) {
		if (!stack.isEmpty()) {
			if (tryExtract(slot)) {
				return true;
			} else {
				SLibUtils.insertOrDrop((ServerLevel) obj.level(), obj, stack.copyAndClear());
			}
		}
		return false;
	}

	private boolean tryInsert(EquipmentSlot slot) {
		if (storedEquipment.get(slot).isEmpty()) {
			ItemStack stack = obj.getItemBySlot(slot);
			obj.setItemSlot(slot, ItemStack.EMPTY);
			storedEquipment.set(slot, stack);
			if (slot == EquipmentSlot.MAINHAND && obj instanceof Player player) {
				hotbarIndex = player.getInventory().getSelectedSlot();
			}
			return true;
		}
		return false;
	}

	private boolean tryExtract(EquipmentSlot slot) {
		ItemStack stack = storedEquipment.get(slot);
		if (!stack.isEmpty()) {
			storedEquipment.set(slot, ItemStack.EMPTY);
			if (slot == EquipmentSlot.MAINHAND && obj instanceof Player player) {
				player.getInventory().add(hotbarIndex, stack);
				hotbarIndex = -1;
			} else {
				obj.setItemSlot(slot, stack);
			}
			return true;
		}
		return false;
	}
}
