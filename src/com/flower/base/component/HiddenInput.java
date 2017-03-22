package com.flower.base.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * ��������(wicket.markup.html.form.HiddenField)��װΪͨ��Panel�ӿ����
 * 
 * ������������ҳ���з��ò��ɼ������ݣ�һ��ɷ���js��Ҫ�Ĳ����������ڿͻ�<br>
 * ���������(��֤�����ص�)
 */
public class HiddenInput extends Panel {
	private static final long serialVersionUID = 1L;

	final private Component m_field;

	/**
	 * ���캯��
	 * @param id
	 * @param valueMap
	 * @param field
	 */
	public HiddenInput(String id, ValueMap valueMap, Field field) {
		super(id);

		m_field = new HiddenField<Object>("input", new PropertyModel<Object>(
				valueMap, field.name));

		add(m_field);
	}

	/**
	 * �����ڲ����������
	 * 
	 * @return
	 */
	public Component getFieldComponent() {
		return m_field;
	}
}
