/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.impl.client.event;

import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import org.jetbrains.annotations.Nullable;

public interface OutlineDataAttachment {
	@Nullable
	OutlineEntityEvent.OutlineData slib$getOutlineData();
}
