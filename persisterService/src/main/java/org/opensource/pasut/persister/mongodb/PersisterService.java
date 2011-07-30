package org.opensource.pasut.persister.mongodb;

import java.util.List;

public interface PersisterService {
	public <T> T insert(T object)throws Exception;
	<T> List<T> find(Class<T> clazz)throws Exception;
}
