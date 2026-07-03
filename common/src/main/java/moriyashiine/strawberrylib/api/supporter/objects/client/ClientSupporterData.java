package moriyashiine.strawberrylib.api.supporter.objects.client;

import net.minecraft.client.OptionInstance;

public record ClientSupporterData<T>(String optionKey, OptionInstance<T> option) {
}
