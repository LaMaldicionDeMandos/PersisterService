package org.pasut.persister.operators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.pasut.persister.GsonMongoMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;

public abstract class Operator {
	public abstract void perform(BasicDBObjectBuilder builder);
	
	private static final GsonMongoMapper wrapper = new GsonMongoMapper();
	private final ObjectMapper mapper = new ObjectMapper();
	private final static Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();
	protected static boolean isWrapperType(Class<?> clazz){
        return WRAPPER_TYPES.contains(clazz);
    }
	
	@SuppressWarnings("unchecked")
	protected String getId(Object value){
		Map<String, Object> map = mapper.convertValue(value,HashMap.class);
		return (String)map.remove("id");
	}
	
	protected BasicDBObject build(String key, Object value){
		BasicDBObject res = new BasicDBObject();
		res.append(key + "._id", getId(value));
		return res;
	}
		
	protected void build(String key, Object value, BasicDBObjectBuilder builder) {
		builder.append(key + "._id", getId(value));
	}
	
	protected Object wrap(Object value){
		if(isWrapperType(value.getClass())) return value;
		if(value.getClass().isEnum()) return value.toString();
		return wrapper.toDbObject(value);
	}
	
	protected boolean isComplex(Object value){
		if(isWrapperType(value.getClass())) return false;
		if(value.getClass().isEnum()) return false;
		return true;
	}
	
	private static HashSet<Class<?>> getWrapperTypes(){
        HashSet<Class<?>> ret = new HashSet<Class<?>>();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        ret.add(String.class);
        return ret;
    }  
}
