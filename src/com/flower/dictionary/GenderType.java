package com.flower.dictionary;
/**
 * �Ա�
 * @author ljp
 *
 */
public enum GenderType {
	MALE(10,"��"),
	FEMALE(20,"Ů"),;
	private int id;
	private String name;
	GenderType(int id, String name){
		this.id = id;
		this.name = name;
	}
	public int getID(){
		return id;
	}
	public String getName(){
		return name;
	}
	
}
