package com.flower.entity;

import com.flower.db.field.AbstractTable;
import com.flower.db.field.Field;
import com.flower.db.field.type.DateTimeType;
import com.flower.db.field.type.IntType;
import com.flower.db.field.type.StringType;

/**
 * 人员信息表
 */
public class TbFlowerUser extends AbstractTable {

	public static final String TABLE = "TB_FLOWER_USER";

	public static final Field ID = new Field("编号", "USER_ID", new IntType(), true, true);

	public static final Field USER_NAME = new Field("用户名", "USER_NAME", new StringType(16), true);

	public static final Field TRUE_NAME = new Field("真实姓名", "TRUE_NAME", new StringType(16), true);

	public static final Field PASSWORD = new Field("密码", "PASSWORD", new StringType(64), true);
	
	//{@link GenderType}
	public static final Field GENDER = new Field("性别", "USER_GENDER", new IntType(), true);
	
	public static final Field ADDRESS = new Field("地址", "ADDRESS", new StringType(256), true);

	// @link UserType
	public static final Field USER_TYPE = new Field("用户类型", "USER_TYPE", new IntType(), true);

	// 邮编
	public static final Field POSTALCODE = new Field("邮编", "PASSWORD", new StringType(64), true);
	
	// 手机
	public static final Field TELEPHONE = new Field("手机", "TELEPHONE", new StringType(11));
	/**
	 * @link UserStatus
	 */
	public static final Field USER_STATUS = new Field("数据状态", "USER_STATUS", new IntType(), true);
	public static final Field REGISTER_TIME = new Field("注册日期", "REGISTER_TIME", new DateTimeType(), true);
	public static final Field UPDATE_TIME = new Field("更新日期", "UPDATE_TIME", new DateTimeType(), true);

	public static final Field[] ALL_FIELDS = new TbFlowerUser().getArrayTableFields();
}
