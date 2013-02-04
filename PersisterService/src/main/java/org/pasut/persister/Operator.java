package org.pasut.persister;

import com.mongodb.BasicDBObjectBuilder;

public interface Operator {
	abstract void perform(BasicDBObjectBuilder builder);
}
