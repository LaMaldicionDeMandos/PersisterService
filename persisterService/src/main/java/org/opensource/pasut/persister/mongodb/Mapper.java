package org.opensource.pasut.persister.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

class Mapper {
	private static final ObjectMapper mapper = 	new ObjectMapper();
	
	public static <T> DBObject toDbObject(T value) throws Exception{
		BasicDBObject object = new BasicDBObject();
		@SuppressWarnings("unchecked")
		Map<String, Object> describe = BeanUtils.describe(value);
		String id = (String) describe.remove("id");
		describe.remove("class");
		if(id == null)
			id = UUID.randomUUID().toString();
		describe.put("_id", id);
		object.putAll(describe);
		return object;
	}
	
	public static <T> T fromDbObject(DBObject object, Class<T> clazz) throws Exception{
			return (T) mapper.readValue(object.toString(), clazz);
	}
	
	public static <T> List<T> fromDbObject(List<DBObject> list, Class<T> clazz) throws Exception{
		List<T> newList = new ArrayList<T>();
		for (DBObject dbObject : list) {
			newList.add(fromDbObject(dbObject, clazz));
		}
		return newList;
	}
}
