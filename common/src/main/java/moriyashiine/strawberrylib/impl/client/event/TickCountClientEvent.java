package moriyashiine.strawberrylib.impl.client.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.multiplayer.ClientLevel;

public class TickCountClientEvent implements ClientTickEvents.EndLevelTick {
	public static void init() {
		ClientTickEvents.END_LEVEL_TICK.register(new TickCountClientEvent());
	}

	public static short tickCount = 0;

	@Override
	public void onEndTick(ClientLevel level) {
		tickCount++;
	}
}
