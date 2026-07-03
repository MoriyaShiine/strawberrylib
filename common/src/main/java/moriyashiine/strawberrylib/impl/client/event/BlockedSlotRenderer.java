package moriyashiine.strawberrylib.impl.client.event;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;

public interface BlockedSlotRenderer {
	Identifier BLOCKED_SPRITE = Identifier.withDefaultNamespace("textures/item/barrier.png");

	boolean slib$renderBlocked();

	void slib$setRenderBlocked(boolean renderBlocked);

	default void slib$maybeRender(GuiGraphicsExtractor graphics, Slot slot) {
		if (slib$renderBlocked()) {
			slib$setRenderBlocked(false);
			graphics.blit(RenderPipelines.GUI_TEXTURED, BLOCKED_SPRITE, slot.x, slot.y, 0, 0, 16, 16, 16, 16, 16, 16, 0x7FFFFFFF);
		}
	}
}
