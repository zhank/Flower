package com.flower.base.component;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.HiddenField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * 将隐藏域(wicket.markup.html.form.HiddenField)封装为通用Panel接口组件
 * 
 * 隐藏域用于在页面中放置不可见的数据，一般可放置js需要的参数，或者在客户<br>
 * 端输出数据(如证书下载等)
 */
public class HiddenInput extends Panel {
	private static final long serialVersionUID = 1L;

	final private Component m_field;

	/**
	 * 构造函数
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
	 * 返回内部的输入组件
	 * 
	 * @return
	 */
	public Component getFieldComponent() {
		return m_field;
	}
}
