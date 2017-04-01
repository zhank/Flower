package com.flower.db.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * 
 * Purpose:ChoiceInput扩展类，数据的显示可以按照放进去的顺序排序
 * 
 * @author yzf
 * @version 1.1.0 2007-2-15
 */
public class ChoiceInputEx extends Panel {
	private static final long serialVersionUID = 1L;

	final private DropDownChoice<Object> m_field;

	/**
	 * 支持使用Properties来构建下拉框，ranges中的key是返回值（value），values是显示值，如 1: "第一个选项"
	 * 
	 * @param id
	 * @param properties
	 *            界面输入将放入其中
	 * @param ranges
	 *            里面的对象是Object[]，Object[0]存放值，Object[1]存放显示标题
	 * @param field
	 * @param notNull
	 */
	public ChoiceInputEx(String id, ValueMap properties,
			final List<Object> data, final Field field, final boolean notNull) {
		super(id);

		IModel<Object> model = new PropertyModel<Object>(properties, field.name);
		if (data != null) {
			final Map<Object, Object> values = new HashMap<Object, Object>();
			for (int i = 0; i < data.size(); i++) {
				values.put(((Object[]) (data.get(i)))[0],
						((Object[]) (data.get(i)))[1]);
			}

			final List<Object> checkData = new ArrayList<Object>();
			for (int i = 0; i < data.size(); i++) {
				checkData.add(((Object[]) (data.get(i)))[0]);
			}

			m_field = new DropDownChoice<Object>("sel", model, checkData,
					new MapChoiceRenderer<Object, Object>(values, checkData));
		} else
			m_field = new DropDownChoice<Object>("sel", model,
					new ArrayList<Object>());

		// v4.3.9 xuji 2008-6-11 支持查询时选择null值 +{{
		m_field.setNullValid(!notNull);
		// }}

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