package org.pasut.persister;

public interface JsonMapper {
	public <T> String toJson(T object);
	public <T> T fromJson(String json, Class<T> clazz);
	public <T> T fromJson(String json);
}
