package moriyashiine.strawberrylib.impl.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

public interface StrawberryLibService {
	StrawberryLibService INSTANCE = ServiceLoader.load(StrawberryLibService.class, StrawberryLibService.class.getClassLoader()).findFirst().orElseThrow(() -> new NoSuchElementException("Unable to load %s service!".formatted(StrawberryLibService.class.getName())));

	void playMuffledStepSound(Entity entity, BlockState state, BlockPos pos);
}
