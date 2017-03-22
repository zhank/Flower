package com.flower.constant;

public enum UserType {
	
	ADMIN_TYPE(10, "管理员"), USER_TYPE(20, "用户");
	
	public int id;
	public String desc;
	
	private UserType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

}
