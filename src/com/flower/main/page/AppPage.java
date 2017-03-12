package com.flower.main.page;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;

import com.flower.main.panel.DefaultPanel;
import com.flower.main.panel.Login;

/**
 * Wicket��һ��Pageҳ��
 */
public class AppPage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	/**
	 * pageҳ�洴��
	 */
	public AppPage() {
		this.add(new Login(getBodyName()).add(new AttributeModifier("style", "height:100%;")));
	}
	
	public void initBody(Panel bodyPanel) {
		if(bodyPanel != null){
			//�����������Panel
			this.toPanel(bodyPanel);
		}else{/*
			// ���Ϊ������Label���棬��ʾ��ҳ�棬����ᱨ��
			this.toPanel(new DefaultPanel(getBodyName()));
		*/}
	}
	
	/**
	 * �滻Panel���
	 * @param panel
	 */
	public void toPanel(Panel panel){
		this.addOrReplace(panel);
	}
	
	/**
	 * ��ʾ������Ϣ
	 */
	public void toError(Exception e){
		this.toPanel(new DefaultPanel(getBodyName(), e.getMessage()));
	}
	
	/**
	 * ��ȡҳ�����岿�ֵ�wicket ID
	 * @return
	 */
	public String getBodyName(){
		return "wBody";
	}
}
