/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.item.GlintLayersRenderState;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
	@Inject(method = "update", at = @At("TAIL"))
	private void slib$supporterGlint(ItemRenderState renderState, ItemStack stack, ItemDisplayContext displayContext, World world, HeldItemContext heldItemContext, int seed, CallbackInfo ci) {
		GlintLayersRenderState glintLayersRenderState = new GlintLayersRenderState();
		if (heldItemContext != null && heldItemContext.getEntity() instanceof PlayerEntity player && SupporterInit.isSupporter(player)) {
			SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
			glintLayersRenderState.glintLayers = GlintLayers.getLayers(stack.contains(DataComponentTypes.EQUIPPABLE) ? supporterComponent.getEquippableGlintColor() : supporterComponent.getGlintColor());
		}
		renderState.setData(GlintLayersRenderState.KEY, glintLayersRenderState);
	}
}
