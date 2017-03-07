/*
 * Created on 2006-11-6
 *
 * @author renwei
 * 
 * Copyright (C) 2006 KOAL SOFTWARE.
 */
package com.flower.db;

import java.util.List;
import java.util.Map;

import com.flower.db.field.Field;

public interface IBizDB {
	/**
	 * �����¼
	 * @param table ����
	 * @param dataMap �������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @throws Exception
	 */
	public void insert(String table, Map<String,Object> dataMap) throws Exception;

	/**
	 * ���±���¼�Ķ���ֶ�
	 * @param table ����
	 * @param dataMap ���������ݵ�Map�����е�key�����ֶ�����value�Ƕ�Ӧ���ֶε�����
	 * @param condition �������
	 * @param condValues ���������б�
	 * @throws Exception
	 */
	public void update(String table, Map<String,Object> dataMap, String condition, List<Object> condValues) throws Exception;

	/**
	 * ���±���¼��һ���ֶ�
	 * @param table ����
	 * @param name �������ֶ���
	 * @param value ����������
	 * @param condition �޲������������
	 * @throws Exception
	 */
	public void update(String table, String name, Object value, String condition) throws Exception;

	/**
	 * ɾ����¼
	 * @param table ����
	 * @param condition �������
	 * @param condValues ���������б�
	 * @throws Exception
	 */
	public void delete(String table, String preparedCond, List<Object> condValues) throws Exception;

	/**
	 * ��ȡһ����¼����map����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param mustUnique �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return ����map
	 * @throws Exception
	 */
	public Map<String,Object> getOneRowAsMap(String table, Field[] fields, String condition, List<Object> condValues, boolean mustUnique)
			throws Exception;

	/**
	 * ��ȡһ����¼�������鷽ʽ���ء�
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param mustUnique �Ƿ����Ψһ����ΪTrue����ʵ�����ж�����¼�����׳��쳣
	 * @return
	 * @throws Exception
	 */
	public Object[] getOneRowAsArray(String table, Field[] fields, String condition, List<Object> condValues, boolean mustUnique)
			throws Exception;

	/**
	 * ͨ�ò�ѯ����List[map1, map2...]��ʽ����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param start ��¼�Ŀ�ʼλ��
	 * @param max ��������¼����
	 * @param orderBy �����ֶ���
	 * @param groupBy �����ֶ���
	 * @param bAsc �Ƿ�����
	 * @return ��List[map1, map2...]��ʽ���أ�colsָ�����ֶ����ݣ����Ϊmax��
	 * @throws Exception
	 */
	public List<Map<String,Object>> searchAsMapList(String table, Field[] fields, String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc) throws Exception;

	/**
	 * ͨ�ò�ѯ����List[array1, array2...]��ʽ����
	 * @param table ����
	 * @param cols ��Ҫ���ص��ֶ����б�
	 * @param condition �������
	 * @param condValues ���������б�
	 * @param start ��¼�Ŀ�ʼλ��
	 * @param max ��������¼����
	 * @param orderBy �����ֶ���
	 * @param groupBy �����ֶ���
	 * @param bAsc �Ƿ�����
	 * @return ��List[array1, array2...]��ʽ���أ�colsָ�����ֶ�����
	 * @throws Exception
	 */
	public List<Object[]> searchAsArrayList(String table, Field[] fields, String condition, List<Object> condValues, int start, int max,
			String orderBy, String groupBy, boolean bAsc) throws Exception;

	/**
	 * �õ���¼����
	 * @param table ����
	 * @param condition �������
	 * @param condValues ��������б�
	 * @param distinct ���ݿ�distinct
	 * @return ���������ļ�¼����
	 * @throws Exception
	 */
	public int getCount(String table, String condition, List<Object> condValues, String distinct) throws Exception;
}