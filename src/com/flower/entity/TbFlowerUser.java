package com.flower.entity;

import com.flower.db.field.AbstractTable;
import com.flower.db.field.Field;
import com.flower.db.field.type.DateTimeType;
import com.flower.db.field.type.IntType;
import com.flower.db.field.type.StringType;

/**
 * ��Ա��Ϣ��
 */
public class TbFlowerUser extends AbstractTable {

	public static final String TABLE = "TB_FLOWER_USER";

	public static final Field ID = new Field("���", "USER_ID", new IntType(), true, true);

	public static final Field USER_NAME = new Field("�û���", "USER_NAME", new StringType(16), true);

	public static final Field TRUE_NAME = new Field("��ʵ����", "TRUE_NAME", new StringType(16), true);

	public static final Field PASSWORD = new Field("����", "PASSWORD", new StringType(64), true);
	
	//{@link GenderType}
	public static final Field GENDER = new Field("�Ա�", "USER_GENDER", new IntType(), true);
	
	public static final Field ADDRESS = new Field("��ַ", "ADDRESS", new StringType(256), true);

	// @link UserType
	public static final Field USER_TYPE = new Field("�û�����", "USER_TYPE", new IntType(), true);

	// �ʱ�
	public static final Field POSTALCODE = new Field("�ʱ�", "PASSWORD", new StringType(64), true);
	
	// �ֻ�
	public static final Field TELEPHONE = new Field("�ֻ�", "TELEPHONE", new StringType(11));
	/**
	 * @link UserStatus
	 */
	public static final Field USER_STATUS = new Field("����״̬", "USER_STATUS", new IntType(), true);
	public static final Field REGISTER_TIME = new Field("ע������", "REGISTER_TIME", new DateTimeType(), true);
	public static final Field UPDATE_TIME = new Field("��������", "UPDATE_TIME", new DateTimeType(), true);

	public static final Field[] ALL_FIELDS = new TbFlowerUser().getArrayTableFields();
}
