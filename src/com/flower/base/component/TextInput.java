package com.flower.base.component;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * ��װ��panel�е�input���
 */
public class TextInput extends Panel {
	private static final long serialVersionUID = 1L;

	final private TextField<Object> m_field;

	public TextInput(String id, ValueMap valueMap, Field field,
			boolean notNull) {
		super(id);

		m_field = new TextField<Object>(id, new PropertyModel<Object>(valueMap,
				field.name));
		m_field.setRequired(notNull);
		add(m_field);
	}

	/**
	 * �����ڲ����������
	 * 
	 * @return
	 */
	public TextField<Object> getFieldComponent() {
		return m_field;
	}
}
