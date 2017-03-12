package com.flower.dictionary;
/**
 * ÐÔ±ð
 * @author ljp
 *
 */
public enum GenderType {
	MALE(10,"ÄÐ"),
	FEMALE(20,"Å®"),;
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
