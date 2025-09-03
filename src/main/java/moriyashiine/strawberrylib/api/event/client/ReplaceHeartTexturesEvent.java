/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface ReplaceHeartTexturesEvent {
	Event<ReplaceHeartTexturesEvent> EVENT = EventFactory.createArrayBacked(ReplaceHeartTexturesEvent.class, events -> player -> {
		List<ReplaceHeartTexturesEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(ReplaceHeartTexturesEvent::getPriority));
		for (ReplaceHeartTexturesEvent event : sortedEvents) {
			@Nullable TextureSet textureSet = event.getTextureSet(player);
			if (textureSet != null) {
				return textureSet;
			}
		}
		return null;
	});

	default int getPriority() {
		return 1000;
	}

	@Nullable TextureSet getTextureSet(PlayerEntity player);

	record TextureSet(Identifier fullTexture, Identifier fullBlinkingTexture,
					  Identifier halfTexture, Identifier halfBlinkingTexture,
					  Identifier hardcoreFullTexture, Identifier hardcoreFullBlinkingTexture,
					  Identifier hardcoreHalfTexture, Identifier hardcoreHalfBlinkingTexture) {
	}
}
