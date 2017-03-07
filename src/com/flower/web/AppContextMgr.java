package com.flower.web;

/**
 * 
 * Purpose:全局参数管理
 * 
 * 
 * @see
 * @since 1.0
 */
public class AppContextMgr {
	/**
	 * 系统名称
	 */
	static String htmlTitle = "Elim鲜花之城";

	/**
	 * 创作者名称
	 */
	static String htmlFooter = "南京研发小分队荣誉出品  建议屏幕分辨率：1280x800以上";
	
	

	public static String getHtmlTitle() {
		return htmlTitle;
	}

	public static void setHtmlTitle(String htmlTitle) {
		AppContextMgr.htmlTitle = htmlTitle;
	}

	public static String getHtmlFooter() {
		return htmlFooter;
	}

	public static void setHtmlFooter(String htmlFooter) {
		AppContextMgr.htmlFooter = htmlFooter;
	}
}