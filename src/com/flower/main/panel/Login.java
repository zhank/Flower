package com.flower.main.panel;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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

	public Login(final Page page, String id) {
		super(id);
		this.setOutputMarkupId(true);
		this.add(creatLoginPanel(page));
		
		this.add(new Label("htmlFooter", AppContextMgr.getHtmlFooter()));
	}

	/**
	 * ��½���� author zhangkai date 2017��3��7��
	 * 
	 * @return
	 */
	public Component creatLoginPanel(Page page) {
		WebMarkupContainer loginWc = new WebMarkupContainer("loginWc");
		loginWc.add(new Label("welTitle", new Model<String>("���ջ�����ӭ��")));
		
		ModalWindow modal = new ModalWindow("modalWindow");
		this.add(modal);
		setModal(page, modal, "�û�ע��", new int[]{800, 400, 800, 400});
		
		//�û�ע��
		AjaxLink<String> registLink = new AjaxLink<String>("rigistLin") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modal.show(target);
			}
		};
		
		loginWc.add(registLink);
		return loginWc;
	}
	public void setModal(final Page page, ModalWindow modal, String title, int[] modalSize) {
		modal.setInitialHeight(450);
		modal.setInitialWidth(650);
		modal.setMinimalHeight(450);
		modal.setMinimalWidth(650);
		modal.setTitle(title);
		modal.setCssClassName("modalCss");
		
		modal.setPageCreator(new ModalWindow.PageCreator(){
			private static final long serialVersionUID = 1L;

			@Override
			public Page createPage() {
				Page page = new RegistPage();
				return page;
			}
		});
	}
}
