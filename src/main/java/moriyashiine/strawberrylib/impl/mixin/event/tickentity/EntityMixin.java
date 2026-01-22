/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.impl.mixin.event.tickentity;

import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract Level level();

	@Inject(method = "baseTick", at = @At("TAIL"))
	private void slib$tickEntity(CallbackInfo ci) {
		TickEntityEvent.EVENT.invoker().tick(level(), (Entity) (Object) this);
	}
}
