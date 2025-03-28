/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.client.render.RenderPhase.*;

public record GlintLayers(RenderLayer glintTranslucent, RenderLayer glint, RenderLayer entityGlint,
						  RenderLayer armorEntityGlint) {
	private static final Map<GlintColor, GlintLayers> LAYERS = new HashMap<>();

	static {
		for (GlintColor color : GlintColor.values()) {
			if (color == GlintColor.PURPLE) {
				LAYERS.put(color, new GlintLayers(RenderLayer.getGlintTranslucent(), RenderLayer.getGlint(), RenderLayer.getEntityGlint(), RenderLayer.getArmorEntityGlint()));
			} else {
				LAYERS.put(color, of(color));
			}
		}
	}

	public static GlintLayers currentLayer = null;

	public static GlintLayers getLayers(GlintColor color) {
		return LAYERS.get(color);
	}

	private static GlintLayers of(GlintColor color) {
		Identifier itemName = StrawberryLib.id("textures/misc/enchanted_glint_item_" + color.getName() + ".png");
		Identifier entityName = StrawberryLib.id("textures/misc/enchanted_glint_armor_" + color.getName() + ".png");

		RenderLayer glintTranslucent = RenderLayer.of(
				"glint_translucent_" + color.getName(),
				1536,
				RenderPipelines.GLINT,
				RenderLayer.MultiPhaseParameters.builder()
						.texture(new RenderPhase.Texture(itemName, TriState.DEFAULT, false))
						.texturing(GLINT_TEXTURING)
						.target(ITEM_ENTITY_TARGET)
						.build(false)
		);
		RenderLayer glint = RenderLayer.of(
				"glint_" + color.getName(),
				1536,
				RenderPipelines.GLINT,
				RenderLayer.MultiPhaseParameters.builder()
						.texture(new RenderPhase.Texture(itemName, TriState.DEFAULT, false))
						.texturing(GLINT_TEXTURING)
						.build(false)
		);
		RenderLayer entityGlint = RenderLayer.of(
				"entity_glint_" + color.getName(),
				1536,
				RenderPipelines.GLINT,
				RenderLayer.MultiPhaseParameters.builder()
						.texture(new RenderPhase.Texture(itemName, TriState.DEFAULT, false))
						.texturing(ENTITY_GLINT_TEXTURING)
						.build(false)
		);
		RenderLayer armorEntityGlint = RenderLayer.of(
				"armor_entity_glint_" + color.getName(),
				1536,
				RenderPipelines.GLINT,
				RenderLayer.MultiPhaseParameters.builder()
						.texture(new RenderPhase.Texture(entityName, TriState.DEFAULT, false))
						.texturing(ARMOR_ENTITY_GLINT_TEXTURING)
						.layering(VIEW_OFFSET_Z_LAYERING)
						.build(false)
		);
		return new GlintLayers(glintTranslucent, glint, entityGlint, armorEntityGlint);
	}
}
