package com.flower.db.field.type;

import java.io.Serializable;

/**
 * 数据校验类型
 */
public abstract class ValidateType implements Serializable {
	private static final long serialVersionUID = 1L;

	// 允许输入的最大长度
	protected int maxLength = 65535;
	
	// 是否允许为空的标志
	protected boolean notNull = false;
	
	/**
	 * 获取数据库类型
	 * @return
	 */
	public abstract int getSqlType();
	
	/**
	 * 获取是否允许为空
	 * 
	 * @return 不允许为空时返回true,否则返回false
	 */
	public boolean notNull() {
		return notNull;
	}

	/**
	 * 设置是否允许为空
	 * 
	 * @param 不允许为空时置为true,否则置为false
	 * @return
	 */
	public final ValidateType setNotNull(boolean notNull) {
		this.notNull = notNull;
		return this;
	}
}
