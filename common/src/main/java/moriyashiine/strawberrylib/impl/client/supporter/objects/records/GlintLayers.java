package moriyashiine.strawberrylib.impl.client.supporter.objects.records;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.TextureTransform;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public record GlintLayers(
		Function<Identifier, RenderType> armorCutoutNoCullGlint,
		Function<Identifier, RenderType> entitySolidGlint,
		Function<Identifier, RenderType> itemCutoutGlint,
		Function<Identifier, RenderType> itemCutoutGlintSpecial,
		Function<Identifier, RenderType> itemTranslucentGlint,
		Function<Identifier, RenderType> itemTranslucentGlintSpecial,
		RenderType patternedShieldGlint,
		RenderType trimmedArmorGlint) {
	private static final Map<GlintColor, GlintLayers> LAYERS = new HashMap<>();

	static {
		for (GlintColor color : GlintColor.values()) {
			if (color != GlintColor.PURPLE) {
				LAYERS.put(color, of(color));
			}
		}
	}

	public static @Nullable GlintLayers currentLayer = null;

	public static @Nullable GlintLayers getLayers(GlintColor color) {
		if (color == GlintColor.PURPLE) {
			return null;
		}
		return LAYERS.get(color);
	}

	private static GlintLayers of(GlintColor color) {
		Identifier armorId = StrawberryLib.id("textures/misc/enchanted_glint_armor_" + color.getName() + ".png");
		Identifier itemId = StrawberryLib.id("textures/misc/enchanted_glint_item_" + color.getName() + ".png");

		Function<Identifier, RenderType> armorCutoutNoCullGlint = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ARMOR_CUTOUT_NO_CULL_GLINT)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", armorId)
							.setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
							.affectsCrumbling()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("armor_cutout_no_cull_glint_" + color.getName(), state);
				}
		);
		Function<Identifier, RenderType> entitySolidGlint = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ENTITY_SOLID_GLINT)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", itemId)
							.setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.affectsCrumbling()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("entity_solid_glint_" + color.getName(), state);
				}
		);
		Function<Identifier, RenderType> itemCutoutGlint = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ITEM_CUTOUT_GLINT)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", itemId)
							.setTextureTransform(TextureTransform.GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.affectsCrumbling()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("item_cutout_glint_" + color.getName(), state);
				}
		);
		Function<Identifier, RenderType> itemCutoutGlintSpecial = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ITEM_CUTOUT_GLINT_SPECIAL)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", itemId)
							.setTextureTransform(TextureTransform.GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.affectsCrumbling()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("item_cutout_glint_special_" + color.getName(), state);
				}
		);
		Function<Identifier, RenderType> itemTranslucentGlint = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ITEM_TRANSLUCENT_GLINT)
							.setOitPipelines(RenderPipelines.OIT_ITEM_GLINT)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", itemId)
							.setTextureTransform(TextureTransform.GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.affectsCrumbling()
							.sortOnUpload()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("item_translucent_glint_" + color.getName(), state);
				}
		);
		Function<Identifier, RenderType> itemTranslucentGlintSpecial = Util.memoize(
				texture -> {
					RenderSetup state = RenderSetup.builder(RenderPipelines.ITEM_TRANSLUCENT_GLINT_SPECIAL)
							.setOitPipelines(RenderPipelines.OIT_ITEM_GLINT_SPECIAL)
							.withTexture("Sampler0", texture)
							.withTexture("GlintSampler", itemId)
							.setTextureTransform(TextureTransform.GLINT_TEXTURING)
							.useLightmap()
							.useOverlay()
							.affectsCrumbling()
							.sortOnUpload()
							.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
							.createRenderSetup();
					return RenderType.create("item_translucent_glint_special_" + color.getName(), state);
				}
		);
		RenderType patternedShieldGlint = RenderType.create(
				"patterned_shield_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", itemId)
						.setTextureTransform(TextureTransform.ENTITY_GLINT_TEXTURING)
						.withForcedSolidModelPhase()
						.createRenderSetup()
		);
		RenderType trimmedArmorGlint = RenderType.create(
				"trimmed_armor_glint_" + color.getName(),
				RenderSetup.builder(RenderPipelines.GLINT)
						.withTexture("Sampler0", armorId)
						.setTextureTransform(TextureTransform.ARMOR_ENTITY_GLINT_TEXTURING)
						.setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
						.withForcedSolidModelPhase()
						.createRenderSetup()
		);
		return new GlintLayers(armorCutoutNoCullGlint, entitySolidGlint, itemCutoutGlint, itemCutoutGlintSpecial, itemTranslucentGlint, itemTranslucentGlintSpecial, patternedShieldGlint, trimmedArmorGlint);
	}
}
