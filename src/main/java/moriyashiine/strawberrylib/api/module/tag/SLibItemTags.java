/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.module.tag;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class SLibItemTags {
	public static final TagKey<Item> UNTRIMMABLE_ARMOR = TagKey.of(RegistryKeys.ITEM, StrawberryLib.id("untrimmable_armor"));
}
