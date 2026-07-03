package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.client.event.BlockedSlotRenderer;
import moriyashiine.strawberrylib.impl.common.component.entity.StoredEquipmentComponent;
import moriyashiine.strawberrylib.impl.common.init.StrawberryLibEntityComponents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin implements BlockedSlotRenderer {
	@Unique
	private boolean renderBlocked = false;

	@Override
	public boolean slib$renderBlocked() {
		return renderBlocked;
	}

	@Override
	public void slib$setRenderBlocked(boolean shouldRenderBlockedSprite) {
		renderBlocked = shouldRenderBlockedSprite;
	}

	@ModifyExpressionValue(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;getItem()Lnet/minecraft/world/item/ItemStack;", ordinal = 0))
	private ItemStack slib$preventEquipmentUsage(ItemStack original, @Local(argsOnly = true) Slot slot) {
		if (slot instanceof CreativeModeInventoryScreen.SlotWrapper wrapper) {
			slot = wrapper.target;
		}
		if (slot.container instanceof Inventory inventory) {
			StoredEquipmentComponent storedEquipment = StrawberryLibEntityComponents.STORED_EQUIPMENT.get(inventory.player);
			for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
				if (equipmentSlot != EquipmentSlot.MAINHAND && (equipmentSlot == EquipmentSlot.OFFHAND ? Inventory.SLOT_OFFHAND : equipmentSlot.getIndex(Inventory.INVENTORY_SIZE)) == slot.getContainerSlot()) {
					ItemStack equippedStack = storedEquipment.getStoredStack(equipmentSlot);
					if (!equippedStack.isEmpty()) {
						renderBlocked = true;
						return equippedStack;
					}
				}
			}
			if (storedEquipment.getHotbarIndex() == slot.getContainerSlot()) {
				renderBlocked = true;
				return storedEquipment.getStoredStack(EquipmentSlot.MAINHAND);
			}
		}
		return original;
	}
}
