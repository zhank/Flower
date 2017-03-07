package com.flower.main.act;

import java.io.Serializable;

import org.apache.wicket.Page;

public interface IPageAction extends Serializable{
	/**
	 * 得到操作名称，一般用于显示
	 * 
	 * @return 操作名称
	 */
	public String getActName();
	
	public void exec(Page page, Object param);

}