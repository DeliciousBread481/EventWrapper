package io.github.lounode.eventwrapper.utils;

public enum LogicalSide {
	CLIENT,
	SERVER;

	LogicalSide() {}

	public boolean isServer() {
		return !this.isClient();
	}

	public boolean isClient() {
		return this == CLIENT;
	}
}
