package org.pasut.persister.operators;

import com.mongodb.BasicDBObjectBuilder;

public class Identiry extends Operator {
	private final String id;
	
	public Identiry(String id){
		this.id = id;
	}
	@Override
	public void perform(BasicDBObjectBuilder builder) {
		builder.append("_id", id);

	}

}
