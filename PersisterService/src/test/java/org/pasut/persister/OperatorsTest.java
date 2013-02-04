package org.pasut.persister;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.pasut.persister.operators.All;
import org.pasut.persister.operators.And;
import org.pasut.persister.operators.Equal;
import org.pasut.persister.operators.GreaterOrEqualThan;
import org.pasut.persister.operators.GreaterThan;
import org.pasut.persister.operators.In;
import org.pasut.persister.operators.LessOrEqualThan;
import org.pasut.persister.operators.LessThan;
import org.pasut.persister.operators.Nor;
import org.pasut.persister.operators.NotEqual;
import org.pasut.persister.operators.NotIn;
import org.pasut.persister.operators.Or;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class OperatorsTest {
	private PersisterService mongo;
	
	@Before
	public void setUp() throws Exception {
		mongo = new MongoPersisterService("persister-service-test", "localhost", 27017);
		DB db = new Mongo("localhost", 27017).getDB("persister-service-test");
		DBCollection testCollection = db.getCollection("test");
		testCollection.drop();
	}

	@Test
	public void testEquals() {
		insert(2);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new Equal("iValue", 1));
		assertEquals(1, list.size());
		SimpleObject object = list.get(0);
		assertEquals(1, object.getiValue());
	}

	@Test
	public void testNotEquals() {
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new NotEqual("iValue", 1));
		assertEquals(4, list.size());
	}

	@Test
	public void testGreaterThan() {
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new GreaterThan("iValue", 2));
		assertEquals(2, list.size());
		SimpleObject object = list.get(0);
		assertEquals(3, object.getiValue());
		object = list.get(1);
		assertEquals(4, object.getiValue());
	}

	@Test
	public void testGreaterOrEqualThan() {
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new GreaterOrEqualThan("iValue", 3));
		assertEquals(2, list.size());
		SimpleObject object = list.get(0);
		assertEquals(3, object.getiValue());
		object = list.get(1);
		assertEquals(4, object.getiValue());
	}

	@Test
	public void testLessThan() {
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new LessThan("iValue", 2));
		assertEquals(2, list.size());
		SimpleObject object = list.get(0);
		assertEquals(0, object.getiValue());
		object = list.get(1);
		assertEquals(1, object.getiValue());
	}
	
	@Test
	public void testLessOrEqualThan() {
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new LessOrEqualThan("iValue", 1));
		assertEquals(2, list.size());
		SimpleObject object = list.get(0);
		assertEquals(0, object.getiValue());
		object = list.get(1);
		assertEquals(1, object.getiValue());
	}
	
	@Test
	public void testAll(){
		SimpleObjectWithCollection object = new SimpleObjectWithCollection("A", 3);
		mongo.insert(object);
		List<SimpleObjectWithCollection> list = mongo.find(SimpleObjectWithCollection.class, new All("collection", "A", 3));
		assertEquals(1, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new All("collection", "A", 3, "B"));
		assertEquals(0, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new All("collection", "A"));
		assertEquals(1, list.size());
	}

	@Test
	public void testIn(){
		SimpleObjectWithCollection object = new SimpleObjectWithCollection("A", 3);
		mongo.insert(object);
		List<SimpleObjectWithCollection> list = mongo.find(SimpleObjectWithCollection.class, new In("collection", 3, "B"));
		assertEquals(1, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new In("collection", 8));
		assertEquals(0, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new In("collection", "A", 8, "B"));
		assertEquals(1, list.size());
	}

	@Test
	public void testNotIn(){
		SimpleObjectWithCollection object = new SimpleObjectWithCollection("A", 3);
		mongo.insert(object);
		List<SimpleObjectWithCollection> list = mongo.find(SimpleObjectWithCollection.class, new NotIn("collection", 3, "B"));
		assertEquals(0, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new NotIn("collection", 8));
		assertEquals(1, list.size());
		list = mongo.find(SimpleObjectWithCollection.class, new NotIn("collection", "A", 8, "B"));
		assertEquals(0, list.size());
	}
	
	@Test
	public void testAnd(){
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new And(new GreaterThan("iValue", 1), new LessOrEqualThan("iValue", 3)));
		assertEquals(2, list.size());
		assertEquals(2, list.get(0).getiValue());
		assertEquals(3, list.get(1).getiValue());
	}

	@Test
	public void testNor(){
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new Nor(new GreaterThan("iValue", 3), new LessOrEqualThan("iValue", 1)));
		assertEquals(2, list.size());
		assertEquals(2, list.get(0).getiValue());
		assertEquals(3, list.get(1).getiValue());
	}

	@Test
	public void testOr(){
		insert(5);
		List<SimpleObject> list = mongo.find(SimpleObject.class, new Or(new GreaterThan("iValue", 3), new LessOrEqualThan("iValue", 0)));
		assertEquals(2, list.size());
		assertEquals(0, list.get(0).getiValue());
		assertEquals(4, list.get(1).getiValue());
	}
	
	@Test
	public void testComplex(){
		ComplexObject object = new ComplexObject("A", 5);
		mongo.insert(object);
		List<ComplexObject> list = mongo.find(ComplexObject.class, new Equal("object", object.getObject()));
		assertEquals(1, list.size());
	}
	
	private void insert(int count){
		for(int i=0;i<count;i++){
			SimpleObject object = new SimpleObject("A", i);
			mongo.insert(object);			
		}
	}

}
