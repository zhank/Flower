package com.flower.constant;

public enum UserType {
	
	ADMIN_TYPE(10, "����Ա"), USER_TYPE(20, "�û�");
	
	public int id;
	public String desc;
	
	private UserType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

}
