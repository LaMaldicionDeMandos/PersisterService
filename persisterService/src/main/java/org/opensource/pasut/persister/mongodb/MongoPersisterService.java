package org.opensource.pasut.persister.mongodb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.opensource.pasut.persister.mongodb.annotaions.Persistable;
import org.opensource.pasut.persister.mongodb.exceptions.PersistenceException;

import com.mongodb.BasicDBList;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoPersisterService implements PersisterService {
	private Mongo mongo;
	private DB db;
	public MongoPersisterService(String dbName, String mongoHost,int mongoPort) throws Exception{
		mongo = new Mongo(mongoHost,mongoPort);
		db = mongo.getDB(dbName);
	}
	
	public <T> T insert(T object) throws Exception{
		DBCollection collection = db.getCollection(getCollectionName(object.getClass()));
		DBObject dbObject = Mapper.toDbObject(object);
		if(dbObject.get("_id")==null){
			dbObject.put("_id", UUID.randomUUID().toString());
			collection.insert(dbObject);
		}
		else
			collection.save(dbObject);
		@SuppressWarnings("unchecked")
		T newObject = (T)Mapper.fromDbObject(dbObject, object.getClass());
		return newObject;
	}
	
	public <T> List<T> find(Class<T> clazz)throws Exception{
		DBCollection collection = db.getCollection(getCollectionName(clazz));
		List<DBObject> list = collection.find().toArray();
		return Mapper.fromDbObject(list, clazz);
	}
	
	public <T> List<T> find(T example,Collection<String> properties)throws Exception{
		DBCollection collection = db.getCollection(getCollectionName(example.getClass()));
		List<DBObject> list = collection.find(cleanExample(Mapper.toDbObject(example),properties)).toArray();
	
		@SuppressWarnings("unchecked")
		List<T> result = (List<T>)Mapper.fromDbObject(list, example.getClass());
		return result;
	}
	
	private DBObject cleanExample(DBObject dbObject,Collection<String> properties) {
		List<String> keysToRemove = new ArrayList<String>();
		for (String key : dbObject.keySet()) {
			if(!properties.contains(key)) 
				keysToRemove.add(key);
			else if(dbObject.get(key) instanceof Collection<?>)
				new ObjectMapper().convertValue(dbObject.get(key), BasicDBList.class);
		}
		for(String key : keysToRemove)
			dbObject.removeField(key);

		return dbObject;
	}

	private <T> String getCollectionName(Class<T> clazz) throws Exception{
		Persistable annotation = clazz.getAnnotation(Persistable.class);
		if(annotation==null) throw new PersistenceException(clazz);
		return annotation.value();
	}
}
