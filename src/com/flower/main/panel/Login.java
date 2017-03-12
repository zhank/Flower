package com.flower.main.panel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.flower.web.AppContextMgr;

/**
 * ��½���� author zhangk date 2017��3��7��
 */
public class Login extends Panel {

	private static final long serialVersionUID = 1L;

	public Login(String id) {
		super(id);
		this.setOutputMarkupId(true);
		this.add(creatLoginPanel());
		
		
		this.add(new Label("htmlFooter", AppContextMgr.getHtmlFooter()));
	}

	/**
	 * ��½���� author zhangkai date 2017��3��7��
	 * 
	 * @return
	 */
	public Component creatLoginPanel() {
		WebMarkupContainer loginWc = new WebMarkupContainer("loginWc");
		loginWc.add(new Label("welTitle", new Model<String>("���ջ�����ӭ��")));
		return loginWc;
	}
}
