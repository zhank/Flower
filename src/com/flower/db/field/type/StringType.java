package com.flower.db.field.type;

/**
 * 字符类型
 *
 */
public class StringType extends ValidateType {

	private static final long serialVersionUID = 1L;
	
	public StringType() {
	}
	
	/**
	 * @param maxLength 最大长度
	 */
	public StringType(int maxLength){
		this.maxLength = maxLength;
	}

	@Override
	public int getSqlType() {
		return java.sql.Types.VARCHAR;
	}

}
