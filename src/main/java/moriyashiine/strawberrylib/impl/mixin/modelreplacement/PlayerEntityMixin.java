/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.mixin.modelreplacement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@ModifyReturnValue(method = "getBaseDimensions", at = @At("RETURN"))
	private EntityDimensions slib$modelReplacement(EntityDimensions original, EntityPose pose) {
		if (isPartOfGame()) {
			LivingEntity replacement = SLibUtils.getModelReplacement((PlayerEntity) (Object) this);
			if (replacement != null) {
				return replacement.getBaseDimensions(pose);
			}
		}
		return original;
	}
}
