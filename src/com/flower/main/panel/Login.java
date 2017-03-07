package com.flower.main.panel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.flower.web.AppContextMgr;

/**
 * 登陆界面 author zhangk date 2017年3月7日
 */
public class Login extends Panel {

	private static final long serialVersionUID = 1L;

	public Login(String id) {
		super(id);
		this.setOutputMarkupId(true);
		//this.add(new Label("htmlTitle", AppContextMgr.getHtmlTitle()));
		this.add(creatLoginPanel());
		this.add(new Label("htmlFooter", AppContextMgr.getHtmlFooter()));
	}

	/**
	 * 登陆界面 author zhangkai date 2017年3月7日
	 * 
	 * @return
	 */
	public Component creatLoginPanel() {
		WebMarkupContainer loginWc = new WebMarkupContainer("loginWc");
		loginWc.add(new Label("welTitle", new Model<String>("以琳花房欢迎您")));
		return loginWc;
	}
}
