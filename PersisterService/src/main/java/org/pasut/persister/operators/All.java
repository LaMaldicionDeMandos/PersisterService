package org.pasut.persister.operators;

import java.util.Arrays;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObjectBuilder;

public class All implements Operator {
	private final String key;
	private final Object[] values;
	
	public All(String key, Object... values){
		this.key = key;
		this.values = values;
	}
	public void perform(BasicDBObjectBuilder builder) {
		BasicBSONObject operation = new BasicBSONObject();
		BasicDBList list = new BasicDBList();
		list.addAll(Arrays.asList(values));
		operation.put("$all", list);
		builder.append(key, operation);
	}

}
