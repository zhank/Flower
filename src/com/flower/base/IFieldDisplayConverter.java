package com.flower.base;

/**
 * 数据转换接口<br>
 * 
 * 用于把从数据库中读出的数据进行转换，比如：<br>
 * 1、将数据库中存储的时间格式化后展示;<br>
 * 2、将简单整数结果转换为字符串说明;<br>
 * 3、将过长的数据缩略显示;
 * 
 * @author xujun
 * 
 */
public interface IFieldDisplayConverter {

	public String convert(Object obj);
}
