package org.pasut.persister;

import org.codehaus.jackson.annotate.JsonProperty;

@Entity("test")
public class SimpleObject {
	@JsonProperty("_id")
	private String id;
	private String sValue;
	private int iValue;
	
	public SimpleObject(String sValue, int iValue){
		this.sValue = sValue;
		this.iValue = iValue;
	}
	
	public String getsValue() {
		return sValue;
	}

	public int getiValue() {
		return iValue;
	}
	
	public void setiValue(int value){
		this.iValue = value;
	}

	@Override
	public boolean equals(Object o){
		if(!(o instanceof SimpleObject)) return false;
		return 	((SimpleObject)o).getiValue() == iValue &&
				((SimpleObject)o).getsValue().equals(sValue);
	}
	
	@Override
	public int hashCode(){
		return iValue + sValue.hashCode();
	}
}
