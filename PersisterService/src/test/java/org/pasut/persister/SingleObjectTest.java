package org.pasut.persister;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class SingleObjectTest {
	private PersisterService mongo;
	
	@Before
	public void setUp() throws Exception {
		mongo = new MongoPersisterService("persister-service-test", "localhost", 27017);
		DB db = new Mongo("localhost", 27017).getDB("persister-service-test");
		DBCollection testCollection = db.getCollection("test");
		testCollection.drop();
	}

	@Test
	public void testInsert() {
		SimpleObject object = new SimpleObject("A", 1);
		mongo.insert(object);
		List<SimpleObject> list = mongo.find(SimpleObject.class);
		assertEquals(Arrays.asList(new SimpleObject("A", 1)), list);
	}
	
	@Test
	public void testUpdate(){
		SimpleObject object = new SimpleObject("A", 1);
		mongo.insert(object);
		List<SimpleObject> list = mongo.find(new SimpleObject("A",0), new String[]{"sValue"});
		object = list.get(0);
		object.setiValue(2);
		mongo.update(object);
		list = mongo.find(SimpleObject.class);
		assertEquals(Arrays.asList(new SimpleObject("A", 2)), list);
	}

}
