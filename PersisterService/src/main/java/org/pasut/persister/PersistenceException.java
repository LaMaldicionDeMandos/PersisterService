package org.pasut.persister;

public class PersistenceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenceException(Class<?> clazz){
		super("The class " + clazz.getCanonicalName() + "don't has the Persistable annotation");
	}
}
