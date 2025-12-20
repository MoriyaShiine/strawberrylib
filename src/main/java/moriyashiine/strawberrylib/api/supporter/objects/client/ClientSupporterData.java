/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.strawberrylib.api.supporter.objects.client;

import net.minecraft.client.option.SimpleOption;

public record ClientSupporterData<T>(String optionKey, SimpleOption<T> option, T defaultOption, Reader<T> reader,
									 Writer<T> writer,
									 PayloadSender<T> payloadSender) {
	public void read(String key) {
		option().setValue(reader().read(key));
	}

	public String write() {
		return writer().write(option().getValue());
	}

	public String defaultName() {
		return writer().write(defaultOption());
	}

	public interface Reader<T> {
		T read(String key);
	}

	public interface Writer<T> {
		String write(T value);
	}

	public interface PayloadSender<T> {
		void send(T value);
	}
}
