package com.flower.main;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import com.flower.main.page.AppPage;

/**
 * Application对象在系统中主 要管理相关信息的配置
 *
 */
public class MainApp extends WebApplication {

	/**
	 * 通过 getHomePage 这个方法就可以定义用户请求时的默认页面
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		this.getMarkupSettings().setStripWicketTags(true);
		return AppPage.class;
	}

}
