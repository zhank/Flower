package com.flower.dictionary;
/**
 * 
 * @author ljp
 *
 */
public enum UserStatus {
	REVOKED(-1,"��ע��"),
	NORMAL(2000,"����"),;
	private int id;
	private String name;
	UserStatus(int id, String name){
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
