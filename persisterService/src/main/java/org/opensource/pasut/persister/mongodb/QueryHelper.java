package org.opensource.pasut.persister.mongodb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

class QueryHelper {
	public <T> DBObject createExample(T example, Collection<String> properties){
		return createDBObjectExample(Mapper.toDbObject(example), properties);
	}
	
	public DBObject createDBObjectExample(final DBObject object, Collection<String> keys){
		DBObject dbObject = new BasicDBObject();
		for(String key : keys){
			dbObject.put(key, getProperty(object,key));
		}
		return dbObject;
	}
	
	private Object getProperty(final DBObject object, String key){
		String[] arrayKey = key.split("\\.");
		if(arrayKey.length==1){
			if(!(object.get(key) instanceof Collection<?>))
				return object.get(key);
			else{
				return toBasicList((Collection<?>)object.get(key));
			}
		}
		return getProperty(new BasicDBObject((Map<?, ?>)object.get(arrayKey[0])), key.substring(key.indexOf(".")+1));
	}
	
	private <T> BasicDBList toBasicList(Collection<T> collection){
		Collection<T> copy = new ArrayList<T>(collection);
		return (BasicDBList) new ObjectMapper().convertValue(copy, BasicDBList.class);
	}
}
