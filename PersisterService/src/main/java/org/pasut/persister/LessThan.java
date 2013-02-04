package org.pasut.persister;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObjectBuilder;

public class LessThan implements Operator {
	private final String key;
	private final Object value;
	
	public LessThan(String key, Object value){
		this.key = key;
		this.value = value;
	}
	
	public void perform(BasicDBObjectBuilder builder) {
		BasicBSONObject operation = new BasicBSONObject();
		operation.put("$lt", value);
		builder.append(key, operation);
	}

}
