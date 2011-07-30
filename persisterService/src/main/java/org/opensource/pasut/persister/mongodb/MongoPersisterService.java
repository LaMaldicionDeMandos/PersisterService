package org.opensource.pasut.persister.mongodb;

import java.util.List;

import org.opensource.pasut.persister.mongodb.annotaions.Persistable;
import org.opensource.pasut.persister.mongodb.exceptions.PersistenceException;

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
		collection.insert(Mapper.toDbObject(object));
		return object;
	}
	
	public <T> List<T> find(Class<T> clazz)throws Exception{
		DBCollection collection = db.getCollection(getCollectionName(clazz));
		List<DBObject> list = collection.find().toArray();
		return Mapper.fromDbObject(list, clazz);
	}
	
	private <T> String getCollectionName(Class<T> clazz) throws Exception{
		Persistable annotation = clazz.getAnnotation(Persistable.class);
		if(annotation==null) throw new PersistenceException(clazz);
		return annotation.value();
	}
}
