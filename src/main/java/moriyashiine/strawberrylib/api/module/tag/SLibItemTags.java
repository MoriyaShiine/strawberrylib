/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.strawberrylib.api.module.tag;

import moriyashiine.strawberrylib.impl.common.StrawberryLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SLibItemTags {
	public static final TagKey<Item> UNTRIMMABLE_ARMOR = TagKey.create(Registries.ITEM, StrawberryLib.id("untrimmable_armor"));
}
