package com.flower.db.field;

import com.flower.db.field.type.DateTimeType;
import com.flower.db.field.type.IntType;
import com.flower.db.field.type.StringType;

/**
 * ��Ա��Ϣ��
 */
public class TbDemoUser {

	public static final String TABLE = "TB_DEMO_USER";

	public static final Field ID = new Field("���", "USER_ID", new IntType(),
			true, true);
	public static final Field CODE = new Field("���֤��", "USER_CODE",
			new StringType(18), true);
	public static final Field NAME = new Field("����", "USER_NAME",
			new StringType(16), true);
	/**
	 * {@link GenderType}
	 */
	public static final Field GENDER = new Field("�Ա�", "USER_GENDER", new IntType(), true);
	/**
	 * {@link DegreeType}
	 */
	public static final Field DEGREE = new Field("ѧ��", "USER_DEGREE", new IntType(), true);
	
	public static final Field ORG_ID = new Field("������λ", "ORG_ID",
			new IntType(), true);
	/**
	 * @link UserStatus
	 */
	public static final Field STATUS = new Field("����״̬", "USER_STATUS",
			new IntType(), true);
	public static final Field INDATE = new Field("��������", "USER_INDATE",
			new DateTimeType(), true);
	
	public static final Field[] ALL_FIELDS = new Field[] {ID, CODE,
			NAME, GENDER, DEGREE, ORG_ID, STATUS, INDATE};
}
