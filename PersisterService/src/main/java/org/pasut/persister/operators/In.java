package org.pasut.persister.operators;

import java.util.Arrays;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObjectBuilder;

public class In extends Operator {
	private final String key;
	private final Object[] values;
	
	public In(String key, Object... values){
		this.key = key;
		this.values = values;
	}
	public void perform(BasicDBObjectBuilder builder) {
		BasicBSONObject operation = new BasicBSONObject();
		BasicDBList list = new BasicDBList();
		boolean areComplex = false;
		for(int i=0;i<values.length;i++){
			if(isComplex(values[i])){
				areComplex = true;
				values[i] = getId(values[i]);
			}else
				values[i] = wrap(values[i]);
		}
		list.addAll(Arrays.asList(values));
		operation.put("$in", list);
		if(areComplex){
			builder.append(key + "._id", operation);
		}else{
			builder.append(key, operation);
		}
	}

}
