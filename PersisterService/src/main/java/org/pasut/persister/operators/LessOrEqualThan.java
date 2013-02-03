package org.pasut.persister.operators;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;

public class LessOrEqualThan implements Operator {
	private final String key;
	private final Object value;
	
	public LessOrEqualThan(String key, Object value){
		this.key = key;
		this.value = value;
	}
	
	public void perform(BasicDBObjectBuilder builder) {
		BasicBSONObject operation = new BasicBSONObject();
		operation.put("$lte", value);
		builder.append(key, operation);
	}

}
