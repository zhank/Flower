package com.flower.main.panel;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.value.ValueMap;

import com.flower.constant.SexType;
import com.flower.db.field.Field;
import com.flower.db.field.FieldViewFactory;
import com.flower.db.field.FvfMapChoice;
import com.flower.entity.TbFlowerUser;
import com.flower.util.FieldDeclare;
import com.flower.util.JsEvent;

public class RegistPage extends WebPage{

	private static final long serialVersionUID = 1L;
	
	public RegistPage() {
		ValueMap valueMap = new ValueMap();
		Map<String, Object> extMap = new HashMap<String, Object>();
		FieldDeclare fields = getFields();
		valueMap.put(TbFlowerUser.GENDER.name, SexType.MALE.getId());
		extMap.put(TbFlowerUser.GENDER.name, SexType.getAllData());
		
		FieldsInput input = new FieldsInput("regist", valueMap, extMap, fields, false) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(Map<String, Object> mapData) {
				
			}

			@Override
			public String getSubmitBtnName() {
				return "����ע��";
			}
		};
		this.add(input);
	}
	
	public FieldDeclare getFields() {
		FieldViewFactory userNameFvf = new FieldViewFactory(TbFlowerUser.USER_NAME);
		userNameFvf.addAjaxEvent(new AjaxEventBehavior(JsEvent.onclick.getName()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				
			}
		});
		return new FieldDeclare(new Field[]{
				userNameFvf.putHtmlAttributeMap("placeholder", "�����ʻ����͵�½��"),
				new FieldViewFactory(TbFlowerUser.PASSWORD).putHtmlAttributeMap("placeholder", "��������ʹ�������ַ����"),
				new FieldViewFactory(TbFlowerUser.USER_STATUS, "ȷ������").putHtmlAttributeMap("placeholder", "���ٴ���������"),
				new FvfMapChoice(TbFlowerUser.GENDER),
				new FieldViewFactory(TbFlowerUser.TELEPHONE).putHtmlAttributeMap("placeholder", "����ʹ�ó����ֻ�"),
				new FieldViewFactory(TbFlowerUser.ADDRESS).putHtmlAttributeMap("placeholder", "����ʹ�ó��õ�ַ"),
		});
	}
}
