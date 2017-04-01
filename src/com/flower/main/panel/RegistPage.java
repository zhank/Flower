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
				return "立即注册";
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
				userNameFvf.putHtmlAttributeMap("placeholder", "您的帐户名和登陆名"),
				new FieldViewFactory(TbFlowerUser.PASSWORD).putHtmlAttributeMap("placeholder", "建议至少使用两种字符组合"),
				new FieldViewFactory(TbFlowerUser.USER_STATUS, "确认密码").putHtmlAttributeMap("placeholder", "请再次输入密码"),
				new FvfMapChoice(TbFlowerUser.GENDER),
				new FieldViewFactory(TbFlowerUser.TELEPHONE).putHtmlAttributeMap("placeholder", "建议使用常用手机"),
				new FieldViewFactory(TbFlowerUser.ADDRESS).putHtmlAttributeMap("placeholder", "建议使用常用地址"),
		});
	}
}
