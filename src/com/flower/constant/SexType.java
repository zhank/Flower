package com.flower.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 性别定义
 * 
 * @author Huangff
 * 
 */
public enum SexType {

	MALE(10, "男"), FEMALE(20, "女"), UNKNOWN(99999, "未知");

	private int id;
	private String name;

	private SexType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public static Integer getId(String name) {
		if (name == null || "".equals(name)) {
			return UNKNOWN.getId();
		}
		if (extMap == null) {
			getAllData();
		}
		for (Integer key : extMap.keySet()) {
			if (name.equals(extMap.get(key))) {
				return key;
			}
		}
		return UNKNOWN.id;
	}

	private static Map<Integer, Object> extMap;

	public static Map<Integer, Object> getAllData() {
		if (extMap == null) {
			extMap = new LinkedHashMap<Integer, Object>();
			for (SexType o : SexType.values()) {
				if (o.getId() != UNKNOWN.getId())
					extMap.put(o.getId(), o.getName());
			}
		}
		return extMap;
	}

	public static SexType valueOf(int id) {
		switch (id) {
		case 10:
			return MALE;
		case 20:
			return FEMALE;
		default:
			return UNKNOWN;
		}
	}
}
