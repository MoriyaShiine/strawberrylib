package moriyashiine.strawberrylib.fabric.impl.mixin.modelreplacement;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
	@Invoker("playMuffledStepSound")
	void slib$playMuffledStepSound(BlockState state);
}
