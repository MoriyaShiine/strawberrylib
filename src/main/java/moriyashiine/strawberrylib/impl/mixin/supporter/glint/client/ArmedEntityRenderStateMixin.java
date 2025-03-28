/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.supporter.glint.client;

import moriyashiine.strawberrylib.impl.client.supporter.objects.records.GlintLayers;
import moriyashiine.strawberrylib.impl.client.supporter.render.entity.state.ArmedEntityRenderStateAddition;
import moriyashiine.strawberrylib.impl.common.init.ModEntityComponents;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.impl.common.supporter.component.entity.SupporterComponent;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmedEntityRenderState.class)
public class ArmedEntityRenderStateMixin implements ArmedEntityRenderStateAddition {
	@Unique
	private GlintLayers leftGlintLayers = null, rightGlintLayers = null;

	@Override
	public GlintLayers slib$getGlintLayers(Arm arm) {
		return arm == Arm.LEFT ? leftGlintLayers : rightGlintLayers;
	}

	@Override
	public void slib$setGlintLayers(Arm arm, GlintLayers layers) {
		if (arm == Arm.LEFT) {
			leftGlintLayers = layers;
		} else {
			rightGlintLayers = layers;
		}
	}

	@Inject(method = "updateRenderState", at = @At("TAIL"))
	private static void slib$supporterGlint(LivingEntity entity, ArmedEntityRenderState state, ItemModelManager itemModelManager, CallbackInfo ci) {
		for (Arm arm : Arm.values()) {
			GlintLayers glintLayers = null;
			if (entity instanceof PlayerEntity player && SupporterInit.isSupporter(player)) {
				SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(player);
				glintLayers = GlintLayers.getLayers(entity.getStackInArm(arm).contains(DataComponentTypes.EQUIPPABLE) ? supporterComponent.getEquippableGlintColor() : supporterComponent.getGlintColor());
			}
			((ArmedEntityRenderStateAddition) state).slib$setGlintLayers(arm, glintLayers);
		}
	}
}
