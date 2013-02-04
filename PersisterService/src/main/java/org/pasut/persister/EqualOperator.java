package org.pasut.persister;

import com.mongodb.BasicDBObjectBuilder;

public class EqualOperator implements Operator {
	private final String key;
	private final Object value;
	
	public EqualOperator(String key, Object value){
		this.key = key;
		this.value = value;
	}
	public void perform(BasicDBObjectBuilder builder) {
		builder.append(key, value);
	}

}
