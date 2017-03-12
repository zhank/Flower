package com.flower.db;

import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.flower.db.field.Field;

import java.util.Set;

/**
 * ���ݿ������
 *
 */
public class BizDB implements IBizDB {
	private static BizDB instance = null;
	
	private String url;
	private String user;
	private String password;
	
	/**
	 * ��ȡ���ݿ����ʵ��
	 * 
	 * @return
	 */
	public static synchronized IBizDB getInstance() {
		if (instance == null) {
			instance = new BizDB();
			// ��ʼ�����ݿ�
			instance.initDB();
		}
		return instance;
	}

	/**
	 * ��ʼ�����ݿ�
	 */
	private void initDB() {
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
	        user = "flower";// �û���,ϵͳĬ�ϵ��˻���
	        password = "flower";// �㰲װʱѡ���õ�����
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}// ����Oracle��������
	}

	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @param bAutoCommit
	 *            �Ƿ��Զ��ύ
	 * @return
	 * @throws Exception
	 */
	public Connection getConn(boolean bAutoCommit) throws Exception {
		Connection conn = DriverManager.getConnection(url, user, password);// ��ȡ����
		conn.setAutoCommit(bAutoCommit);
		return conn;
	}

	/**
	 * �ͷ����ݿ�����
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void closeConn(Connection conn) throws Exception {
		if (conn == null)
			return;
		try {
			if (!conn.getAutoCommit())
				conn.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}

	@Override
	public void insert(String table, Map<String, Object> dataMap) throws Exception {
		// ����������
		if (table == null || dataMap == null) {
			String err = "����insert��������";
			throw new IllegalArgumentException(err);
		}

		// ��ȡ���ݿ�����
		Connection conn = getConn(false);
		try {
			// ���������䣬���磺insert into tb_test (name, type, id) values (?, ?, ?)
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			StringBuffer sql = new StringBuffer(64 + entrySet.size() * 8);
			sql.append("insert into " + table + " (");

			List<Object> values = new ArrayList<Object>(entrySet.size());
			for (Map.Entry<String, Object> entry : entrySet) {
				if (values.size() > 0)
					sql.append(",");

				sql.append(entry.getKey());
				values.add(entry.getValue());
			}
			sql.append(") values (");

			for (int i = 0; i < entrySet.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append("?");
			}
			sql.append(")");

			// ���ò���
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql.toString());
			preparedStatementSetValues(pstmt, values);

			// ִ��
			try {
				pstmt.executeUpdate();
			} finally {
				pstmt.close();
			}
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public void update(String table, Map<String, Object> dataMap, String condition, List<Object> condValues)
			throws Exception {
		// ����������
		if (table == null || dataMap == null) {
			String err = "����update��������";
			throw new IllegalArgumentException(err);
		}

		Connection conn = getConn(false);
		try {
			// ���������䣬���磺update tb_test set name=?, type=?, id=? where xx
			Set<Entry<String, Object>> entrySet = dataMap.entrySet();
			StringBuffer sql = new StringBuffer(64 + entrySet.size() * 8);
			sql.append("update " + table + " set ");

			List<Object> values = new ArrayList<Object>(entrySet.size());
			for (Map.Entry<String, Object> entry : entrySet) {
				if (values.size() > 0)
					sql.append(",");
				sql.append(entry.getKey() + "=?");
				values.add(entry.getValue());
			}

			if (condition != null)
				sql.append(" where " + condition);

			// ���ò���
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			if (condition != null && condValues != null) {
				// ������������������ں���
				values.addAll(condValues);
			}
			preparedStatementSetValues(pstmt, values);

			// ִ��
			try {
				pstmt.executeUpdate();
			} finally {
				pstmt.close();
			}
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public void update(String table, String name, Object value, String condition) throws Exception {
		// ����������
		if (table == null || name == null || value == null || condition == null) {
			String err = "����update��������";
			throw new IllegalArgumentException(err);
		}

		Connection conn = getConn(false);
		try {
			// ���������䣬���磺update tb_test set name=?
			StringBuffer sql = new StringBuffer(64);
			sql.append("update " + table + " set " + name + "=?");
			sql.append(" where " + condition);

			// ���ò���
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			List<Object> values = new ArrayList<Object>();
			values.add(value);
			preparedStatementSetValues(pstmt, values);

			// ִ��
			try {
				pstmt.executeUpdate();
			} finally {
				pstmt.close();
			}
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public void delete(String table, String condition, List<Object> condValues) throws Exception {
		if (table == null) {
			String err = "����delete��������";
			throw new IllegalArgumentException(err);
		}
		Connection conn = getConn(false);
		try {
			if (conn == null) {
				String err = "connection��ȡʧ��";
				throw new Exception(err);
			}

			String sql;
			if (condition == null)
				sql = "delete from " + table;
			else
				sql = "delete from " + table + " where " + condition;

			PreparedStatement pstmt = conn.prepareStatement(sql);

			if (condValues != null) {
				preparedStatementSetValues(pstmt, condValues);
			}
			try {
				pstmt.executeUpdate();
			} finally {
				pstmt.close();
			}
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public Map<String, Object> getOneRowAsMap(String table, Field[] fields, String condition, List<Object> condValues,
			boolean mustUnique) throws Exception {
		Connection conn = getConn(false);
		try {
			List<String> cols = getListFromFields(fields);
			Map<String, Object> map = new HashMap<String, Object>();

			getOneRow(conn, table, cols, condition, condValues, mustUnique, map);
			return map;
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public Object[] getOneRowAsArray(String table, Field[] fields, String condition, List<Object> condValues,
			boolean mustUnique) throws Exception {
		Connection conn = getConn(false);
		try {
			List<String> cols = getListFromFields(fields);
			return getOneRow(conn, table, cols, condition, condValues, mustUnique, null);
		} finally {
			closeConn(conn);
		}
	}
	
	/**
	 * ��ȡһ�����ݵ�ʵ�֣�����map����������map�������鷽ʽ����
	 * 
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param cols
	 *            ��Ҫ���ص��ֶ����б�
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @param mustUnique
	 *            �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @param map
	 *            ����Ϊnull�����÷������ݵ����У����������鷵��
	 * @return ��map��Ϊnull�����÷������ݵ�map������null�����������鷵��
	 * @throws Exception
	 */
	protected Object[] getOneRow(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, boolean mustUnique, Map<String, Object> map) throws Exception {
		if (conn == null || table == null) {
			String err = "����getOneRow��������";
			throw new IllegalArgumentException(err);
		}

		StringBuffer sql = new StringBuffer(32);

		int i;
		if (cols == null)
			sql.append("select * from ");
		else {
			sql.append("select ");
			for (i = 0; i < cols.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append(cols.get(i));
			}
			sql.append(" from ");
		}
		sql.append(table);

		if ((condition != null) && (condition.length() > 0)) {
			sql.append(" where " + condition);
		}

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		try {
			if (condValues != null) {
				preparedStatementSetValues(pstmt, condValues);
			}

			ResultSet rs = pstmt.executeQuery();

			if (!rs.next()) {
				// ������
				String err = "δ����Ҫ���ҵ����ݡ�[" + table + ":" + sql;
				throw new Exception(err);
			}

			Object[] resArray = null;
			if (map != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				for (i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), getRsObj(i, rs, rsmd));
				}
			} else {
				ResultSetMetaData rsmd = rs.getMetaData();
				resArray = new Object[rsmd.getColumnCount()];
				for (i = 1; i <= rsmd.getColumnCount(); i++) {
					resArray[i - 1] = getRsObj(i, rs, rsmd);
				}
			}

			if (mustUnique) {
				if (rs.next()) {
					String err = "�����ҵ������Ψһ��[" + table + ":" + sql;
					throw new Exception(err);
				}
			}
			if (map != null) {
				return null;
			} else {
				return resArray;
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}
	}

	@Override
	public List<Map<String, Object>> searchAsMapList(String table, Field[] fields, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc)
			throws Exception {
		Connection conn = getConn(false);
		try {
			List<String> cols = getListFromFields(fields);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> dataMapList = (List<Map<String, Object>>) search(conn, table, cols, condition,
					condValues, start, max, orderBy, groupBy, bAsc, true);
			return dataMapList;
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, List<Object> condValues,
			int start, int max, String orderBy, String groupBy, boolean bAsc) throws Exception {
		Connection conn = getConn(false);
		try {
			List<String> cols = getListFromFields(fields);
			@SuppressWarnings("unchecked")
			List<Object[]> datasList = (List<Object[]>) search(conn, table, cols, condition, condValues, start, max,
					orderBy, groupBy, bAsc, false);
			return datasList;
		} finally {
			closeConn(conn);
		}
	}

	@Override
	public int getCount(String table, String condition, List<Object> condValues, String distinct) throws Exception {
		if (table == null) {
			String err = "����getCount��������";
			throw new IllegalArgumentException(err);
		}
		Connection conn = getConn(false);
		try {
			if (conn == null) {
				String err = "connection��ȡʧ��";
				throw new Exception(err);
			}
			StringBuffer sql = new StringBuffer(32);
			if (distinct == null)
				sql.append("select count(*) from " + table);
			else
				sql.append("select count(distinct " + distinct + ") from " + table);

			if (condition != null)
				sql.append(" where " + condition);

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			if (condValues != null) {
				preparedStatementSetValues(pstmt, condValues);
			}
			try {

				ResultSet rs = pstmt.executeQuery();

				if (!rs.next()) // ������
					return 0;

				return rs.getInt(1);
			} finally {
					pstmt.close();
			}
		} finally {
			closeConn(conn);
		}
	}

	/**
	 * ��ѯ�ľ���ʵ�֣�������������������map-list����array-list����
	 * 
	 * @param conn
	 *            ���ݿ�����
	 * @param table
	 *            ����
	 * @param cols
	 *            ��Ҫ���ص��ֶ����б�
	 * @param condition
	 *            �������
	 * @param condValues
	 *            ���������б�
	 * @param start
	 *            ��¼�Ŀ�ʼλ��
	 * @param max
	 *            ��������¼����
	 * @param orderBy
	 *            �����ֶ���
	 * @param groupBy
	 *            �����ֶ���
	 * @param bAsc
	 *            �Ƿ�����
	 * @param bMap
	 *            true: ��List[map1, map1...]��ʽ����; false: ��List[array1,
	 *            array2...]��ʽ����
	 * @return ��bMap��������
	 * @throws Exception
	 */
	protected List<?> search(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc, boolean bMap)
			throws Exception {
		if (conn == null || table == null) {
			String err = "����search��������";
			throw new IllegalArgumentException(err);
		}

		// 1 �������
		StringBuffer sql = new StringBuffer(64);
		int i;
		if (cols == null)
			sql.append("select * from ");
		else {
			sql.append("select ");
			for (i = 0; i < cols.size(); i++) {
				if (i > 0)
					sql.append(",");
				sql.append(cols.get(i));
			}
			sql.append(" from ");
		}
		sql.append(table);

		if ((condition != null) && (condition.length() > 0)) {
			sql.append(" where " + condition);
		}

		if (groupBy != null) {
			sql.append(" group by " + groupBy);
		}

		if (orderBy != null) {
			sql.append(" order by " + orderBy);
			if (!bAsc)
				sql.append(" desc");
		}

		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		if (condValues != null) {
			preparedStatementSetValues(pstmt, condValues);
		}

		// 2 ִ�в�ѯ
		List<Object> ret = new ArrayList<Object>();
		try {
			ResultSet rs = pstmt.executeQuery();

			// 3 ������
			int colCount = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();

			int startCounter = 0;
			int fetchCount = 0;
			if (bMap) {
				// map���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Map<String, Object> oneRec = new HashMap<String, Object>();
						for (i = 1; i <= colCount; i++) {
							oneRec.put(rsmd.getColumnName(i), getRsObj(i, rs, rsmd));
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			} else {
				// array���
				while (rs.next()) {
					if (fetchCount == max)
						break;

					if (startCounter >= start) {
						Object[] oneRec = new Object[colCount];
						for (i = 0; i < colCount; i++) {
							oneRec[i] = getRsObj(i + 1, rs, rsmd);
						}
						ret.add(oneRec);
						fetchCount++;
					} else {
						startCounter++;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
		}

		return ret;
	}

	/**
	 * ����ResultSet����е����ݣ���������ݿ������Զ�ת��Ϊjava����
	 * 
	 * @param index
	 * @param rs
	 * @param rsmd
	 * @return
	 * @throws Exception
	 */
	protected static final Object getRsObj(int index, ResultSet rs, ResultSetMetaData rsmd) throws Exception {
		switch (rsmd.getColumnType(index)) {
		case Types.LONGVARBINARY:
		case Types.VARBINARY:
		case Types.BINARY:
		case Types.BLOB:
			byte[] data = rs.getBytes(index);
			if (data != null) {
				return new String(data);
			} else {
				return null;
			}
		case Types.CLOB:
			Clob clob = rs.getClob(index);
			if (clob != null) {
				return clob.getSubString(1, Integer.valueOf(clob.length() + ""));
			} else {
				return null;
			}
		case Types.NUMERIC:
			Integer i = rs.getInt(index);
			if (rs.wasNull())
				i = null;
			return i;
		case Types.BIGINT:
			return new Integer(rs.getInt(index));
		case Types.DECIMAL:
			BigDecimal decimal = rs.getBigDecimal(index);
			if (decimal == null) {
				return null;
			} else {
				return decimal.intValue();
			}
		case Types.DATE:
		case Types.TIMESTAMP:
			Timestamp timestamp = rs.getTimestamp(index);
			if (timestamp == null) {
				return null;
			} else {
				return new java.util.Date(timestamp.getTime());
			}
		case Types.ARRAY:
		case Types.BIT:
		case Types.BOOLEAN:
		case Types.CHAR:
		case Types.DATALINK:
		case Types.DISTINCT:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.INTEGER:
		case Types.JAVA_OBJECT:
		case Types.LONGNVARCHAR:
		case Types.NCHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.NCLOB:
		case Types.NULL:
		case Types.NVARCHAR:
		case Types.OTHER:
		case Types.REAL:
		case Types.REF:
		case Types.ROWID:
		case Types.SMALLINT:
		case Types.SQLXML:
		case Types.STRUCT:
		case Types.TIME:
		case Types.TINYINT:
			return rs.getObject(index);
		default:
			return rs.getObject(index);
		}
	}

	/**
	 * ������ֵ�����͵��ò�ͬ��setXXX����
	 * 
	 * @param pstmt
	 * @param values
	 * @throws Exception
	 */
	protected static final void preparedStatementSetValues(PreparedStatement pstmt, List<Object> values)
			throws Exception {
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) instanceof java.util.Date) {
				java.util.Date date = (java.util.Date) values.get(i);
				pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
			} else {
				pstmt.setObject(i + 1, values.get(i));
			}
		}

	}

	/**
	 * ��ȡFieldList���ֶ����б�
	 * 
	 * @param fields
	 * @return
	 */
	public static List<String> getListFromFields(Field[] fields) {
		if (fields == null) {
			return null;
		}
		List<String> fieldList = new ArrayList<String>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			fieldList.add(fields[i].name);
		}
		return fieldList;
	}
}
