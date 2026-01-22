/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.client.outlineentity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import moriyashiine.strawberrylib.impl.client.event.OutlineDataAttachment;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements OutlineDataAttachment {
	@Unique
	private OutlineEntityEvent.@Nullable OutlineData outlineData = null;

	@Override
	public OutlineEntityEvent.@Nullable OutlineData slib$getOutlineData() {
		return outlineData;
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void slib$outlineEntity(CallbackInfo ci) {
		outlineData = OutlineEntityEvent.EVENT.invoker().getOutlineData((Entity) (Object) this);
	}

	@ModifyReturnValue(method = "getTeamColor", at = @At(value = "RETURN", ordinal = 1))
	private int slib$outlineEntity(int original) {
		if (outlineData != null && outlineData.color().isPresent()) {
			return outlineData.color().getAsInt();
		}
		return original;
	}
}
