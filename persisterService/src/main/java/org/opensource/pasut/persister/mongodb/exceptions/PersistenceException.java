package org.opensource.pasut.persister.mongodb.exceptions;

public class PersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenceException(Class<?> clazz){
		super("The class " + clazz.getCanonicalName() + "don't has the Persistable annotation");
	}

}
