package com.flower.main.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

import com.flower.main.panel.DefaultPanel;
import com.flower.main.panel.Login;

/**
 * Wicket的一个Page页面
 */
public class AppPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	/**
	 * page页面创建
	 */
	public AppPage() {
		this.add(new Login(getBodyName()).add(new AttributeModifier("style", "height:100%;")));
	}
	
	public void initBody(Panel bodyPanel) {
		if(bodyPanel != null){
			//创建主体面板Panel
			this.toPanel(bodyPanel);
		}else{/*
			// 面板为空则用Label代替，显示空页面，否则会报错
			this.toPanel(new DefaultPanel(getBodyName()));
		*/}
	}
	
	/**
	 * 替换Panel面板
	 * @param panel
	 */
	public void toPanel(Panel panel){
		this.addOrReplace(panel);
	}
	
	/**
	 * 显示错误信息
	 */
	public void toError(Exception e){
		this.toPanel(new DefaultPanel(getBodyName(), e.getMessage()));
	}
	
	/**
	 * 获取页面主体部分的wicket ID
	 * @return
	 */
	public String getBodyName(){
		return "wBody";
	}
}
