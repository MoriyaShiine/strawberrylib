/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.event.client;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface ReplaceHeartTexturesEvent {
	Event<ReplaceHeartTexturesEvent> EVENT = EventFactory.createArrayBacked(ReplaceHeartTexturesEvent.class, events -> player -> {
		for (ReplaceHeartTexturesEvent event : events) {
			Optional<TextureSet> textureSet = event.getTextureSet(player);
			if (textureSet.isPresent()) {
				return textureSet;
			}
		}
		return Optional.empty();
	});

	Optional<TextureSet> getTextureSet(PlayerEntity player);

	record TextureSet(Identifier fullTexture, Identifier fullBlinkingTexture,
					  Identifier halfTexture, Identifier halfBlinkingTexture,
					  Identifier hardcoreFullTexture, Identifier hardcoreFullBlinkingTexture,
					  Identifier hardcoreHalfTexture, Identifier hardcoreHalfBlinkingTexture) {
	}
}
