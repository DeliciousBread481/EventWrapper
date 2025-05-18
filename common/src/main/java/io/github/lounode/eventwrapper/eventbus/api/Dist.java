package io.github.lounode.eventwrapper.eventbus.api;

public enum Dist {
	CLIENT,
	DEDICATED_SERVER;

	Dist() {}

	public boolean isDedicatedServer() {
		return !this.isClient();
	}

	public boolean isClient() {
		return this == CLIENT;
	}
}
