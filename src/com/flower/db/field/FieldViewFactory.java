package com.flower.db.field;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.value.ValueMap;

import com.flower.base.IFieldDisplayConverter;
import com.flower.base.component.CheckInput;
import com.flower.base.component.DateInput;
import com.flower.base.component.HiddenInput;
import com.flower.base.component.TextInput;
import com.flower.db.field.type.ValidateType;

/**
 * 缺省的FieldView工厂，根据Field类型、View属性生成view。
 * createInputComp用于生成输入控件，createOutputComp用于生成输出（展示）控件。
 */
public class FieldViewFactory extends Field {
	private static final long serialVersionUID = 1L;
	
	private boolean readonly = false; // 缺省可写
	private boolean visible = true; // 缺省可见
	private boolean escapeModel = true; // 缺省转译HTML标签
	
	public IFieldDisplayConverter fdc = null;
	
	protected Map<String, String> htmlAttributeMap = new HashMap<String, String>();
	
	public FieldViewFactory setVisible(boolean b) {
		visible = b;
		return this;
	}

	public boolean isVisible() {
		return visible;
	}

	public FieldViewFactory setReadonly(boolean b) {
		this.readonly = b;
		return this;
	}

	public boolean isReadonly() {
		return readonly;
	}
	
	public boolean isEscapeModel() {
		return escapeModel;
	}

	public FieldViewFactory setEscapeModel(boolean escapeModel) {
		this.escapeModel = escapeModel;
		return this;
	}
	
	public FieldViewFactory addConverter(IFieldDisplayConverter fdc) {
		this.fdc = fdc;
		return this;
	}

	/**
	 * 构造函数
	 * @param field
	 */
	public FieldViewFactory(Field field) {
		super(field.caption, field.name, field.getType(), field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * 构造函数
	 * @param field
	 * @param caption
	 */
	public FieldViewFactory(Field field, String caption) {
		super(caption, field.name, field.getType(), field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * 构造函数
	 * @param field
	 * @param type
	 */
	public FieldViewFactory(Field field, ValidateType type) {
		super(field.caption, field.name, type, field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * 构造函数
	 * @param field
	 * @param caption
	 * @param type
	 */
	public FieldViewFactory(Field field, String caption, ValidateType type) {
		super(caption, field.name, type, field.notNull, field.isPrimaryKey);
	}
	
	/**
	 * 修改caption和colName，一般用于查询时的时间字段，如起始日期和终止日期
	 * 
	 * @param field
	 * @param caption
	 * @param colName
	 */
	public FieldViewFactory(Field field, String caption, String colName) {
		super(caption, colName, field.getType(), field.notNull,
				field.isPrimaryKey);
	}
	
	/**
	 * 生成输入组件
	 * 
	 * @param id
	 *            组件ID
	 * @param valueMap
	 *            model和view交换数据的map
	 * @param compParamMap
	 *            组件参数相关的数据map，具体与各个组件相关
	 * @param notNull
	 *            是否不允许为null
	 * @return
	 */
	public Component createInputComp(String id, ValueMap valueMap,
			Map<String, Object> compParamMap, boolean notNull) {
		if (isReadonly()) {
			// 只读就只显示label
			return new Label(id, getFieldStringValue(valueMap.get(this.name)));
		}
		ValidateType type = this.getType();

		if (type.getSqlType() == java.sql.Types.DATE) {
			DateInput dateInput = new DateInput(id, valueMap, this, notNull);
			addComponentAttributeValue(dateInput.getFieldComponent());
			return dateInput;
		} else if (type.getSqlType() == java.sql.Types.BOOLEAN) {
			CheckInput checkInput = new CheckInput(id, valueMap, this, notNull);
			addComponentAttributeValue(checkInput.getFieldComponent());
			return checkInput;
		} else {
			if (!this.isVisible()) {
				HiddenInput hiddenInput = new HiddenInput(id, valueMap, this);
				addComponentAttributeValue(hiddenInput.getFieldComponent());
				return hiddenInput;
			} else {
				// 基本input组件
				TextInput textInput = new TextInput(id, valueMap, this,
						notNull);
				addComponentAttributeValue(textInput.getFieldComponent());
				return textInput;
			}

		}
	}
	
	/**
	 * 生成输出组件
	 * 
	 * @param id
	 *            组件ID
	 * @param value
	 *            该field的数据
	 * @return
	 */
	public Component createOutputComp(String id, Object value) {
		Label label = new Label(id, getFieldStringValue(value));
		label.setEscapeModelStrings(escapeModel);
		return label;
	}
	
	/**
	 * 添加html属性值到这个field中去 例如 new
	 * FieldViewFactory(ipField).putHtmlAttributeMap("id", ipField.name),
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public FieldViewFactory putHtmlAttributeMap(String key, String value) {
		this.htmlAttributeMap.put(key, value);
		return this;
	}
	
	/**
	 * 把基本的Html属性添加到Component中去
	 * 
	 * @param comp
	 *            对应的Component
	 */
	public void addComponentAttributeValue(Component comp) {
		if (this.htmlAttributeMap.size() > 0) {
			for (String key : htmlAttributeMap.keySet()) {
				String value = this.htmlAttributeMap.get(key);
				comp.add(new AttributeModifier(key, new Model<String>(value)));
			}
		}
	}
	
	/**
	 * 获取Field前端展示的值，时间即按时间格式显示，需要转化的就按转化器转换
	 * @param value
	 * @return
	 */
	public String getFieldStringValue(Object value) {
		if (value != null) {
			if (fdc != null) {
				return fdc.convert(value);
			}else {
				if (value instanceof Date){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return sdf.format((Date) value);
				}else{
					return value.toString();
				}
			}
		} else{
			return "";
		}
	}
}
