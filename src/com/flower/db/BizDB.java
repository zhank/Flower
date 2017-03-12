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
 * 数据库操作类
 *
 */
public class BizDB implements IBizDB {
	private static BizDB instance = null;
	
	private String url;
	private String user;
	private String password;
	
	/**
	 * 获取数据库操作实例
	 * 
	 * @return
	 */
	public static synchronized IBizDB getInstance() {
		if (instance == null) {
			instance = new BizDB();
			// 初始化数据库
			instance.initDB();
		}
		return instance;
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	        user = "flower";// 用户名,系统默认的账户名
	        password = "flower";// 你安装时选设置的密码
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}// 加载Oracle驱动程序
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param bAutoCommit
	 *            是否自动提交
	 * @return
	 * @throws Exception
	 */
	public Connection getConn(boolean bAutoCommit) throws Exception {
		Connection conn = DriverManager.getConnection(url, user, password);// 获取连接
		conn.setAutoCommit(bAutoCommit);
		return conn;
	}

	/**
	 * 释放数据库连接
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
		// 检查输入参数
		if (table == null || dataMap == null) {
			String err = "调用insert参数错误";
			throw new IllegalArgumentException(err);
		}

		// 获取数据库连接
		Connection conn = getConn(false);
		try {
			// 构造插入语句，形如：insert into tb_test (name, type, id) values (?, ?, ?)
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

			// 设置参数
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(sql.toString());
			preparedStatementSetValues(pstmt, values);

			// 执行
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
		// 检查输入参数
		if (table == null || dataMap == null) {
			String err = "调用update参数错误";
			throw new IllegalArgumentException(err);
		}

		Connection conn = getConn(false);
		try {
			// 构造更新语句，形如：update tb_test set name=?, type=?, id=? where xx
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

			// 设置参数
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			if (condition != null && condValues != null) {
				// 如果有条件参数，加在后面
				values.addAll(condValues);
			}
			preparedStatementSetValues(pstmt, values);

			// 执行
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
		// 检查输入参数
		if (table == null || name == null || value == null || condition == null) {
			String err = "调用update参数错误";
			throw new IllegalArgumentException(err);
		}

		Connection conn = getConn(false);
		try {
			// 构造更新语句，形如：update tb_test set name=?
			StringBuffer sql = new StringBuffer(64);
			sql.append("update " + table + " set " + name + "=?");
			sql.append(" where " + condition);

			// 设置参数
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			List<Object> values = new ArrayList<Object>();
			values.add(value);
			preparedStatementSetValues(pstmt, values);

			// 执行
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
			String err = "调用delete参数错误";
			throw new IllegalArgumentException(err);
		}
		Connection conn = getConn(false);
		try {
			if (conn == null) {
				String err = "connection获取失败";
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
	 * 获取一条数据的实现，根据map参数决定以map还是数组方式返回
	 * 
	 * @param conn
	 *            数据库连接
	 * @param table
	 *            表名
	 * @param cols
	 *            需要返回的字段名列表
	 * @param condition
	 *            条件语句
	 * @param condValues
	 *            条件参数列表
	 * @param mustUnique
	 *            是否必须唯一？若为True，但实际又有多条记录，将抛出异常
	 * @param map
	 *            若不为null，设置返回数据到其中；否则以数组返回
	 * @return 若map不为null，设置返回数据到map，返回null；否则以数组返回
	 * @throws Exception
	 */
	protected Object[] getOneRow(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, boolean mustUnique, Map<String, Object> map) throws Exception {
		if (conn == null || table == null) {
			String err = "调用getOneRow参数错误";
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
				// 无数据
				String err = "未发现要查找的数据。[" + table + ":" + sql;
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
					String err = "所查找的数据项不唯一。[" + table + ":" + sql;
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
			String err = "调用getCount参数错误";
			throw new IllegalArgumentException(err);
		}
		Connection conn = getConn(false);
		try {
			if (conn == null) {
				String err = "connection获取失败";
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

				if (!rs.next()) // 无数据
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
	 * 查询的具体实现，根据最后参数决定是以map-list还是array-list返回
	 * 
	 * @param conn
	 *            数据库连接
	 * @param table
	 *            表名
	 * @param cols
	 *            需要返回的字段名列表
	 * @param condition
	 *            条件语句
	 * @param condValues
	 *            条件参数列表
	 * @param start
	 *            记录的开始位置
	 * @param max
	 *            返回最大记录个数
	 * @param orderBy
	 *            排序字段名
	 * @param groupBy
	 *            分组字段名
	 * @param bAsc
	 *            是否升序
	 * @param bMap
	 *            true: 以List[map1, map1...]形式返回; false: 以List[array1,
	 *            array2...]形式返回
	 * @return 由bMap参数决定
	 * @throws Exception
	 */
	protected List<?> search(Connection conn, String table, List<String> cols, String condition,
			List<Object> condValues, int start, int max, String orderBy, String groupBy, boolean bAsc, boolean bMap)
			throws Exception {
		if (conn == null || table == null) {
			String err = "调用search参数错误";
			throw new IllegalArgumentException(err);
		}

		// 1 构造语句
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

		// 2 执行查询
		List<Object> ret = new ArrayList<Object>();
		try {
			ResultSet rs = pstmt.executeQuery();

			// 3 构造结果
			int colCount = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			colCount = rsmd.getColumnCount();

			int startCounter = 0;
			int fetchCount = 0;
			if (bMap) {
				// map结果
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
				// array结果
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
	 * 返回ResultSet结果中的数据，会根据数据库类型自动转换为java类型
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
	 * 根据数值的类型调用不同的setXXX方法
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
	 * 获取FieldList的字段名列表
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
