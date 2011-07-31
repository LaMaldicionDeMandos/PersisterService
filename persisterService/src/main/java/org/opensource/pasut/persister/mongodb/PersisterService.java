package org.opensource.pasut.persister.mongodb;

import java.util.Collection;
import java.util.List;

public interface PersisterService {
	public <T> T insert(T object)throws Exception;
	<T> List<T> find(Class<T> clazz)throws Exception;
	<T> List<T> find(T example,Collection<String> properties)throws Exception;
}
