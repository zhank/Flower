package com.flower.main.panel;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;
import com.flower.db.field.FieldViewFactory;
import com.flower.entity.TbFlowerUser;
import com.flower.util.FieldDeclare;

public class RegistPage extends WebPage{

	private static final long serialVersionUID = 1L;
	
	public RegistPage() {
		ValueMap valueMap = new ValueMap();
		Map<String, Object> extMap = new HashMap<String, Object>();
		FieldDeclare fields = getFields();
		FieldsInput input = new FieldsInput("regist", valueMap, extMap, fields, false) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(Map<String, Object> mapData) {
				
			}

			@Override
			public String getSubmitBtnName() {
				return "¡¢º¥◊¢≤·";
			}
		};
		this.add(input);
	}
	
	public FieldDeclare getFields() {
		return new FieldDeclare(new Field[]{
				new FieldViewFactory(TbFlowerUser.USER_NAME),
				new FieldViewFactory(TbFlowerUser.PASSWORD),
				new FieldViewFactory(TbFlowerUser.USER_STATUS, "»∑»œ√‹¬Î"),
				new FieldViewFactory(TbFlowerUser.GENDER),
				new FieldViewFactory(TbFlowerUser.TELEPHONE),
				new FieldViewFactory(TbFlowerUser.ADDRESS),
		});
	}
}
