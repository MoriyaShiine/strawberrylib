/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public final class SLibDataUtils {
	public static final Model CROP_CROSS = new Model(Optional.of(StrawberryLib.id("block/crop_cross")), Optional.empty(), TextureKey.CROP);

	public static void generateCropCross(BlockStateModelGenerator generator, Block block, Property<Integer> ageProperty, int... ageTextureIndices) {
		generator.registerItemModel(block.asItem());
		if (ageProperty.getValues().size() != ageTextureIndices.length) {
			throw new IllegalArgumentException();
		} else {
			Int2ObjectMap<Identifier> map = new Int2ObjectOpenHashMap<>();
			BlockStateVariantMap<WeightedVariant> variant = BlockStateVariantMap.models(ageProperty)
					.generate(age -> createWeightedVariant(map.computeIfAbsent(ageTextureIndices[age], stage -> generator.createSubModel(block, "_stage" + stage, CROP_CROSS, TextureMap::crop))));
			generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(variant));
		}
	}

	public static void generateSoilBlock(BlockStateModelGenerator generator, Block block) {
		WeightedVariant mainVariant = modelWithYRotation(
				createModelVariant(TexturedModel.CUBE_BOTTOM_TOP.get(block)
						.textures(textures -> textures.put(TextureKey.BOTTOM, TextureMap.getId(Blocks.DIRT)))
						.upload(block, generator.modelCollector)
				)
		);
		WeightedVariant snowVariant = createWeightedVariant(TextureMap.getSubId(Blocks.GRASS_BLOCK, "_snow"));
		generator.registerTopSoil(block, mainVariant, snowVariant);
	}
}
