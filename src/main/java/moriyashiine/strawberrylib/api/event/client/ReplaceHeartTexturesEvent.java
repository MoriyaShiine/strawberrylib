/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

public interface ReplaceHeartTexturesEvent {
	Event<ReplaceHeartTexturesEvent> EVENT = EventFactory.createArrayBacked(ReplaceHeartTexturesEvent.class, events -> player -> {
		List<ReplaceHeartTexturesEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
		sortedEvents.sort(Comparator.comparingInt(ReplaceHeartTexturesEvent::getPriority));
		for (ReplaceHeartTexturesEvent event : sortedEvents) {
			Optional<TextureSet> textureSet = event.getTextureSet(player);
			if (textureSet.isPresent()) {
				return textureSet;
			}
		}
		return Optional.empty();
	});

	default int getPriority() {
		return 1000;
	}

	Optional<TextureSet> getTextureSet(PlayerEntity player);

	record TextureSet(Identifier fullTexture, Identifier fullBlinkingTexture,
					  Identifier halfTexture, Identifier halfBlinkingTexture,
					  Identifier hardcoreFullTexture, Identifier hardcoreFullBlinkingTexture,
					  Identifier hardcoreHalfTexture, Identifier hardcoreHalfBlinkingTexture) {
	}
}
