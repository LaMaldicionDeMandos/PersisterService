package org.pasut.persister.operators;

import com.mongodb.BasicDBObjectBuilder;

public class Equal extends Operator {
	private final String key;
	private final Object value;
	
	public Equal(String key, Object value){
		this.key = key;
		this.value = value;
	}
	public void perform(BasicDBObjectBuilder builder) {
		builder.append(key, wrap(value));
	}

}
