package com.flower.db.field;

import java.io.Serializable;
import java.sql.Types;

import com.flower.db.field.type.ValidateType;

/**
 * Field代表确定属性的数据的定义，
 * 属性包括该数据的内部标识（name）、类型（type）、界面显示名称（caption）、是否非空（notNull）等。
 *
 */
public class Field implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用于界面显示字符串
	 */
	public final String caption;

	/**
	 * 内部使用的标识，对于数据库字段，也是字段名称
	 */
	public String name;

	/**
	 * 是否非空
	 */
	public final boolean notNull;

	/**
	 * 是否为主键
	 */
	public final boolean isPrimaryKey;

	/**
	 * 字段对应的校验类型
	 */
	private ValidateType type;
	
	/**
	 * 构造函数
	 * 
	 * @param caption	界面显示的名称
	 * @param name		内部数据标识，对于数据库字段是字段名称
	 * @param type		可验证的数据类型
	 */
	public Field(String caption, String name, ValidateType type) {
		this(caption, name, type, false, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param caption	界面显示的名称
	 * @param name		内部数据标识，对于数据库字段是字段名称
	 * @param type		可验证的数据类型
	 * @param notNull	是否非空
	 */
	public Field(String caption, String name, ValidateType type, boolean notNull) {
		this(caption, name, type, notNull, false);
	}

	/**
	 * 构造函数
	 * 
	 * @param caption		界面显示的名称
	 * @param name			内部数据标识，对于数据库字段是字段名称
	 * @param type			可验证的数据类型
	 * @param notNull		是否非空
	 * @param isPrimaryKey	是否为主键，一般定义数据库表时用到
	 */
	public Field(String caption, String name, ValidateType type, boolean notNull, boolean isPrimaryKey) {
		this.caption = caption;
		this.name = name;
		this.notNull = notNull;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;

		if (type != null) {
			type.setNotNull(notNull);
		}
	}

	/**
	 * 获取对应的验证类型
	 */
	public ValidateType getType() {
		return type;
	}

	/**
	 * 根据field的类型，将val转换为name=val的sql格式，目前只支持string和integer类型 是为了方便写简单的查询条件
	 * 
	 * @param val 需要转换为sql语句的数据
	 * @return 转换后的sql字符串
	 */
	public String toSqlEQ(Object val) {
		if (val == null)
			return null;

		switch (getType().getSqlType()) {
		case Types.VARCHAR:
			return name + "='" + val + "'";
		case Types.INTEGER:
		case Types.NUMERIC:
			return name + "=" + val;
		default:
			throw new UnsupportedOperationException("toSqlEQ不支持该类型");
		}
	}
}
