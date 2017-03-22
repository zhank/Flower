package com.flower.main.panel;

import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.FieldViewFactory;
import com.flower.util.FieldDeclare;

abstract public class FieldsInput extends Panel{

	private static final long serialVersionUID = 1L;
	private final FieldDeclare m_inputFields;
	private ValueMap m_valueMap;
	private Map<String, Object> m_extMap;
	private boolean m_notNull = false; // 输入字段中是否做非空校验
	
	public static final String LABEL_NAME = "label";
	public static final String INPUT_NAME = "input";
	
	public FieldsInput(String id, ValueMap valueMap, Map<String, Object> extMap,
			FieldDeclare fields, final boolean notNull) {
		super(id);
		this.m_valueMap = valueMap;
		this.m_extMap = extMap;
		this.m_inputFields = fields;
		this.m_notNull = notNull;
		
		if(valueMap != null) {
			m_valueMap = valueMap;
		} else {
			m_valueMap = new ValueMap();
		}
		
		Form<?> form = new Form<Void>("form");
		this.add(form);
		
		Loop loop = new Loop("loop", m_inputFields.getFieldCount()) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(LoopItem item) {
				int index = item.getIndex();
				FieldViewFactory fvf = m_inputFields.getFvfArray()[index];
				Component label = getInputLabel("label", fvf);
				item.add(label);
				
				Component input = fvf.createInputComp("input", m_valueMap, m_extMap, m_notNull);
				item.add(input);
				input.setVisible(fvf.isVisible());
			}
			
			protected Component getInputLabel(String id, FieldViewFactory fvf) {
				if(fvf.isVisible()) {
					return new Label(id, fvf.caption);
				} else {
					Label label = new Label(id, "");
					return label;
				}
			}
		};
		form .add(loop);
		AjaxButton submitBtn = new AjaxButton("submit", new Model<String>(getSubmitBtnName()), form) {
			private static final long serialVersionUID = 1L;

			protected void onSubmit(AjaxRequestTarget target, Form<?> form){
				FieldsInput.this.onSubmit((Map<String, Object>) m_valueMap);
			}
		};
		form.add(submitBtn);
	}
	
	abstract public void onSubmit(Map<String, Object> mapData);

	
	public String getSubmitBtnName() {
		return "确定";
	}
	
}
