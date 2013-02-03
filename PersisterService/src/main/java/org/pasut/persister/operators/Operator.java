package org.pasut.persister.operators;

import com.mongodb.BasicDBObjectBuilder;

public interface Operator {
	abstract void perform(BasicDBObjectBuilder builder);
}
