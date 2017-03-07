package com.flower.main.page;

import org.apache.wicket.markup.html.panel.Panel;

/**
 * 模态框内部显示的Page
 *
 */
public class ModalPage extends AppPage {

	private static final long serialVersionUID = 1L;
	public final String panelId = "mainPanel";

	public ModalPage() {
	}
	
	public ModalPage(Panel panel){
		initBody(panel);
	}
}
