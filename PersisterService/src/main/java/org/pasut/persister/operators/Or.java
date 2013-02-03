package org.pasut.persister.operators;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObjectBuilder;

public class Or implements Operator {
	private Operator[] operators;
	
	public Or(Operator... operators){
		this.operators = operators;
	}
	
	public void perform(BasicDBObjectBuilder builder) {
		BasicDBList list = new BasicDBList();
		for(Operator operator : operators){
			BasicDBObjectBuilder operatorBuilder = new BasicDBObjectBuilder();
			operator.perform(operatorBuilder);
			list.add(operatorBuilder.get());
		}
		builder.append("$or", list);
	}

}
