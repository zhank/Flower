package com.flower.db.field.type;

/**
 * Int����
 *
 */
public class IntType extends ValidateType {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int getSqlType() {
		return java.sql.Types.INTEGER;
	}

}
