package org.pasut.persister;

import java.util.List;

import org.pasut.persister.operators.Operator;

public interface PersisterService {
	public <T> T insert(T object);
	public <T> List<T> find(Class<T> clazz);
	public <T> T update(T object);
	
	<T> List<T> find(T example,String[] properties);
	<T> T findOne(T example, String[] properties);

	<T> List<T> find(Class<T> clazz, Operator ... operators);
	<T> T findOne(Class<T> clazz, Operator ... operators);
	public <T> long count(Class<T> clazz);
	public <T> long count(T example, String[] properties);

}
