/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.common.event;

import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

public class TickEntitiesEvent implements ServerTickEvents.EndWorldTick {
	@Override
	public void onEndTick(ServerWorld world) {
		for (Entity entity : world.iterateEntities()) {
			TickEntityEvent.EVENT.invoker().tick(world, entity);
		}
	}
}
