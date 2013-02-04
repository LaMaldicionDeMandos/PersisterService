package org.pasut.persister.operators;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;

public class GreaterThan extends Operator {
	private final String key;
	private final Object value;
	
	public GreaterThan(String key, Object value){
		this.key = key;
		this.value = value;
	}
	
	public void perform(BasicDBObjectBuilder builder) {
		BasicBSONObject operation = new BasicBSONObject();
		operation.put("$gt", wrap(value));
		builder.append(key, operation);
	}

}
