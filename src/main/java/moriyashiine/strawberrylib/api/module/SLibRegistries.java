/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module;

import com.mojang.serialization.MapCodec;
import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.particle.ParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class SLibRegistries {
	public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return Blocks.register(RegistryKey.of(RegistryKeys.BLOCK, StrawberryLib.cid(name)), factory, settings);
	}

	public static <T extends Block> MapCodec<T> registerBlockType(String name, MapCodec<T> codec) {
		return Registry.register(Registries.BLOCK_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder<T> builder) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, StrawberryLib.cid(name), builder.build());
	}

	public static <T> ComponentType<T> registerComponentType(String name, ComponentType.Builder<T> builder) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, StrawberryLib.cid(name), builder.build());
	}

	public static <T extends Criterion<?>> T registerCriterion(String name, T criterion) {
		return Registry.register(Registries.CRITERION, StrawberryLib.cid(name), criterion);
	}

	public static <T> ComponentType<T> registerEnchantmentEffectComponentType(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, StrawberryLib.cid(name), builderOperator.apply(ComponentType.builder()).build());
	}

	public static <T extends EnchantmentEntityEffect> MapCodec<T> registerEnchantmentEntityEffectType(String name, MapCodec<T> codec) {
		return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, StrawberryLib.cid(name), codec);
	}

	public static <T extends Entity> EntityType<T> registerEntityType(String name, EntityType.Builder<T> builder) {
		RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, StrawberryLib.cid(name));
		return Registry.register(Registries.ENTITY_TYPE, key.getValue(), builder.build(key));
	}

	public static <T extends LivingEntity> EntityType<T> registerEntityType(String name, EntityType.Builder<T> builder, DefaultAttributeContainer.Builder attributeBuilder) {
		EntityType<T> type = registerEntityType(name, builder);
		FabricDefaultAttributeRegistry.register(type, attributeBuilder);
		return type;
	}

	public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return Items.register(RegistryKey.of(RegistryKeys.ITEM, StrawberryLib.cid(name)), factory, settings);
	}

	public static Item registerItem(String name, Function<Item.Settings, Item> factory) {
		return registerItem(name, factory, new Item.Settings());
	}

	public static Item registerBlockItem(String name, Block block, Item.Settings settings) {
		return registerItem(name, s -> new BlockItem(block, s), settings.useBlockPrefixedTranslationKey());
	}

	public static Item registerBlockItem(String name, Block block) {
		return registerBlockItem(name, block, new Item.Settings());
	}

	public static ItemGroup registerItemGroup(String name, ItemGroup itemGroup) {
		return Registry.register(Registries.ITEM_GROUP, StrawberryLib.cid(name), itemGroup);
	}

	public static ItemGroup registerItemGroup(ItemGroup itemGroup) {
		return registerItemGroup(StrawberryLib.currentModId, itemGroup);
	}

	public static LootConditionType registerLootConditionType(String name, LootConditionType lootConditionType) {
		return Registry.register(Registries.LOOT_CONDITION_TYPE, StrawberryLib.cid(name), lootConditionType);
	}

	public static <T extends ParticleType<?>> T registerParticleType(String name, T particleType) {
		return Registry.register(Registries.PARTICLE_TYPE, StrawberryLib.cid(name), particleType);
	}

	public static <T extends Potion> T registerPotion(String name, T potion) {
		return Registry.register(Registries.POTION, StrawberryLib.cid(name), potion);
	}

	public static <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String name, RecipeSerializer<T> recipeSerializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, StrawberryLib.cid(name), recipeSerializer);
	}

	public static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandlerType(String name, ScreenHandlerType<T> screenHandlerType) {
		return Registry.register(Registries.SCREEN_HANDLER, StrawberryLib.cid(name), screenHandlerType);
	}

	public static SoundEvent registerSoundEvent(String name) {
		Identifier id = StrawberryLib.cid(name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static RegistryEntry<SoundEvent> registerSoundEventReference(String name) {
		Identifier id = StrawberryLib.cid(name);
		return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, StrawberryLib.cid(name), statusEffect);
	}
}
