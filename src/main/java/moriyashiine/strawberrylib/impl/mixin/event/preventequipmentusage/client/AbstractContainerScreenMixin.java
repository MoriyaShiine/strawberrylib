/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.preventequipmentusage.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.strawberrylib.impl.common.component.entity.StoredEquipmentComponent;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
	@Unique
	private static final Identifier BLOCKED_SPRITE = Identifier.withDefaultNamespace("textures/item/barrier.png");

	@Unique
	private boolean renderBlocked = false;

	@ModifyExpressionValue(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;getItem()Lnet/minecraft/world/item/ItemStack;", ordinal = 0))
	private ItemStack slib$preventEquipmentUsage(ItemStack original, @Local(argsOnly = true) Slot slot) {
		if (slot instanceof CreativeModeInventoryScreen.SlotWrapper wrapper) {
			slot = wrapper.target;
		}
		if (slot.container instanceof Inventory inventory) {
			StoredEquipmentComponent storedEquipmentComponent = ModEntityComponents.STORED_EQUIPMENT.get(inventory.player);
			for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
				if (equipmentSlot != EquipmentSlot.MAINHAND && (equipmentSlot == EquipmentSlot.OFFHAND ? Inventory.SLOT_OFFHAND : equipmentSlot.getIndex(Inventory.INVENTORY_SIZE)) == slot.getContainerSlot()) {
					ItemStack equippedStack = storedEquipmentComponent.getStoredStack(equipmentSlot);
					if (!equippedStack.isEmpty()) {
						renderBlocked = true;
						return equippedStack;
					}
				}
			}
			if (storedEquipmentComponent.getHotbarIndex() == slot.getContainerSlot()) {
				renderBlocked = true;
				return storedEquipmentComponent.getStoredStack(EquipmentSlot.MAINHAND);
			}
		}
		return original;
	}

	@Inject(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V"))
	private void slib$preventEquipmentUsage(GuiGraphicsExtractor graphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
		if (renderBlocked) {
			renderBlocked = false;
			graphics.blit(RenderPipelines.GUI_TEXTURED, BLOCKED_SPRITE, slot.x, slot.y, 0, 0, 16, 16, 16, 16, 16, 16, 0x7FFFFFFF);
		}
	}
}
