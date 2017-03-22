package com.flower.db.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * 将下拉框(wicket.markup.html.form.DropDownChoice)封装为通用Panel接口组件
 * 
 * @author renwei
 * @version 0.9
 * @since 0.9
 */
public class ChoiceInput extends Panel {

	private static final long serialVersionUID = 1L;

	final private DropDownChoice<?> m_field;

	/**
	 * 
	 * @param id
	 * @param properties
	 *            界面输入将放入其中
	 * @param ranges
	 *            可选择的数据
	 * @param field
	 * @param notNull
	 */
	public ChoiceInput(String id, ValueMap properties, List<Object> ranges,
			Field field, boolean notNull) {
		super(id);

		IModel<Object> model = new PropertyModel<Object>(properties, field.name);

		if (ranges != null && !ranges.isEmpty()) {
			// v1.2.0 200-07-27 weixh
			// 支持List，但需要满足条件：list每个元素对象数组中第一个是ID，第二个是VALUE　c{{
			if (ranges.get(0) instanceof String) {
				m_field = new DropDownChoice<Object>("sel", model, ranges);
			} else {
				// final List list = ranges;
				m_field = new DropDownChoice<Object>("sel", model, ranges,
						new ListChoiceRenderer<Object>(ranges));

			}
		}
		// }}
		else
			m_field = new DropDownChoice<Object>("sel", model,
					new ArrayList<Object>());
		add(m_field);
	}

	public ChoiceInput(String id, ValueMap properties,
			final Map<Object, Object> ranges, final Field field,
			final boolean notNull) {
		this(id, properties, ranges, field, notNull, null);
	}

	/**
	 * 支持使用Properties来构建下拉框，ranges中的key是返回值（value），values是显示值，如 1: "第一个选项"
	 * 
	 * 
	 * @param id
	 * @param properties
	 *            界面输入将放入其中
	 * @param ranges
	 * @param field
	 * @param notNull
	 */
	public ChoiceInput(String id, ValueMap properties,
			final Map<Object, Object> ranges, final Field field,
			final boolean notNull, final String defSelectedValue) {
		super(id);

		IModel<Object> model = new PropertyModel<Object>(properties, field.name);

		if (ranges != null) {
			final List<Object> values = new ArrayList<Object>();
			for (Object object : ranges.keySet()) {
				values.add(object);
			}
			
			m_field = new DropDownChoice<Object>("sel", model, values,
					new MapChoiceRenderer<Object, Object>(ranges, values)) {
				// zhangjl 2013-9-11 支持查询时选择null值,选择后提交查询后，还保留“请选择”在列表中 +{{
				private static final long serialVersionUID = 1L;

				@Override
				protected CharSequence getDefaultChoice(String selectedValue) {
					if (notNull) {
						return super.getDefaultChoice(selectedValue);
					} else {
						if (null == defSelectedValue) {
							return "\n<option selected=\"selected\" value=\"\">"
									+ "请选择" + "</option>";
						} else {
							return "\n<option selected=\"selected\" value=\"\">"
									+ defSelectedValue + "</option>";
						}
					}
				}
				// }}
			};
		} else
			m_field = new DropDownChoice<Object>("sel", model,
					new ArrayList<Object>());

		// v1.2.0 xuji 2008-6-11 支持查询时选择null值 +{{
		m_field.setNullValid(!notNull);
		// }}

		add(m_field);
	}

	/**
	 * 返回内部的输入组件
	 * 
	 * @return
	 */
	public DropDownChoice<?> getFieldComponent() {
		return m_field;
	}

}
