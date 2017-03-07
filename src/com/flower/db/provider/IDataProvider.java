package com.flower.db.provider;

import java.util.List;

import com.flower.db.field.Field;

/**
 * 列表组件的数据提供接口
 */
public interface IDataProvider{
	/**
	 * 得到记录总个数
	 * @param condition 条件语句
	 * @param condValues 条件参数
	 * @return
	 */
	public int onGetCount(String condition, List<Object> condValues);
	
	/**
	 * 查询记录
	 * @param fields
	 * @param condition
	 * @param condValues
	 * @param start
	 * @param max
	 * @param orderby
	 * @param bAsc
	 * @return
	 */
	public List<Object[]> onSearch(Field[] fields, String condition, 
				List<Object> condValues, int start, int max, String orderby, boolean bAsc);
}
