package com.flower.db.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.flower.db.IBizDB;
import com.flower.db.field.Field;
import com.flower.main.page.AppPage;

/**
 * 让列表等组件从数据库得到数据
 */
public class DBDataProvider implements IDataProvider, Serializable{
	private static final long serialVersionUID = 1L;

	private final IBizDB m_bizDb;
	private final AppPage m_page;
	private final String m_tableName;
	private String m_fixedCond;
	private List<Object> m_fixedCondValues;
	private boolean m_enable = true;
	
	public DBDataProvider(AppPage page, String tableName, IBizDB bizDb) {
		m_page = page;
		m_tableName = tableName;
		m_bizDb = bizDb;
	}
	
	public void setFixedCondition(String cond, List<Object> condValues) {
		m_fixedCond = cond;
		m_fixedCondValues = condValues;
	}

	public void setEnable(boolean b) {
		m_enable = b;
	}
	
	public int onGetCount(String condition, List<Object> condValues) {
		if (!m_enable)
			return 0;
		try {
			if (m_fixedCond != null) {
				String newCond = appendFixedCondition(condition);
				List<Object> newCondValues = appendFixedCondValues(condValues);;
				return m_bizDb.getCount(m_tableName, newCond, newCondValues, null);
			} else 
				return m_bizDb.getCount(m_tableName, condition, condValues, null);
		} catch (Exception e) {
			m_page.toError(e);
			return 0;
		}
	}
	
	/**重载方法加上去重字段
	 * @author zhangmh
	 * @param condition
	 * @param condValues
	 * @param distinctField 去重字段
	 * @return
	 * 2015年11月2日09:08:14
	 */
	public int onGetCount(String condition, List<Object> condValues,String distinctField) {
		if (!m_enable)
			return 0;
		try {
			if (m_fixedCond != null) {
				String newCond = appendFixedCondition(condition);
				List<Object> newCondValues = appendFixedCondValues(condValues);;
				return m_bizDb.getCount(m_tableName, newCond, newCondValues, distinctField);
			} else 
				return m_bizDb.getCount(m_tableName, condition, condValues, distinctField);
		} catch (Exception e) {
			m_page.toError(e);
			return 0;
		}
	}
	
	public List<Object[]> onSearch(Field[] fields, String condition, 
			List<Object> condValues, int start, int max, String orderby, boolean bAsc) {
		if (!m_enable)
			return null;
		try {
			if (m_fixedCond != null) {
				String newCond = appendFixedCondition(condition);
				List<Object> newCondValues = appendFixedCondValues(condValues);;
				return m_bizDb.searchAsArrayList(m_tableName, fields, newCond, newCondValues, start, max, orderby, null, bAsc);
			} else
				return m_bizDb.searchAsArrayList(m_tableName, fields, condition, condValues, start, max, orderby, null, bAsc);
		} catch (Exception e) {
			m_page.toError(e);
			return null;
		}
	}
	
	protected String appendFixedCondition(String cond) {
		if (m_fixedCond == null)
			return cond;
			
		String newCond;
		if (cond == null) {
			newCond = m_fixedCond;  	
		} else {
			newCond = cond + " AND " + m_fixedCond;  	
		}
		
		return newCond;
	}
	
	protected List<Object> appendFixedCondValues(List<Object> condValues) {
		if (m_fixedCondValues == null)
			return condValues;
		
		List<Object> newCondValues = m_fixedCondValues;
		
		if (condValues != null && condValues.size() > 0) {
			newCondValues.addAll(condValues);
		}	
		
		return newCondValues;
	}
	
	protected static List<String> getListFromFields(Field[] fields) {
		List<String> fieldList = new ArrayList<String>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			fieldList.add(fields[i].name);
		}
		return fieldList;
	}
}
