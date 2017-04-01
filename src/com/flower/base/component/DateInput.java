package com.flower.base.component;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.field.Field;

/**
 * 将日期组件(wicket.extensions.markup.html.datepicker.DatePicker)封装为通用Panel接口组件
 * 
 */
public class DateInput extends Panel {
	private static final long serialVersionUID = 1L;

	final private Component m_field;

	public DateInput(String id, final ValueMap properties, final Field field,
			boolean notNull) {
		super(id);

		m_field = new DateField("input", new PropertyModel<Object>(properties,
				field.name), field, notNull);
		DatePicker datePicker = new DatePicker() {

			private static final long serialVersionUID = 1L;

			@Override
			protected String getAdditionalJavaScript() {
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render();";
			}
		};
		datePicker.setShowOnFieldClick(true);
		datePicker.setAutoHide(true);
		m_field.add(datePicker);

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

	class DateField extends TextField<Object> implements ITextFormatProvider {
		private static final long serialVersionUID = 1L;

		public DateField(String name, IModel<Object> model, Field field,
				boolean checkNull) {
			super(name, model);
			setType(Date.class);
		}

		public String getTextFormat() {
			return "yyyy-MM-dd";
		}
	}
}
