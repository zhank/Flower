package com.flower.base.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * 将选择框(wicket.markup.html.form.CheckBox)封装为通用Panel接口组件
 * 
 */
public class CheckInput extends Panel {
	private static final long serialVersionUID = 1L;

	final private Component m_field;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CheckInput(String id, ValueMap valueMap, Field field, boolean notNull) {
		super(id);

		m_field = new CheckBox("bool", new PropertyModel(valueMap, field.name));

		add(m_field);
	}

	/**
	 * 返回内部的输入组件
	 * 
	 * @return
	 */
	public Component getFieldComponent() {
		return m_field;
	}

}
