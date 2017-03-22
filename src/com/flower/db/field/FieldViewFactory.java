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
 * ȱʡ��FieldView����������Field���͡�View��������view��
 * createInputComp������������ؼ���createOutputComp�������������չʾ���ؼ���
 */
public class FieldViewFactory extends Field {
	private static final long serialVersionUID = 1L;
	
	private boolean readonly = false; // ȱʡ��д
	private boolean visible = true; // ȱʡ�ɼ�
	private boolean escapeModel = true; // ȱʡת��HTML��ǩ
	
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
	 * ���캯��
	 * @param field
	 */
	public FieldViewFactory(Field field) {
		super(field.caption, field.name, field.getType(), field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * ���캯��
	 * @param field
	 * @param caption
	 */
	public FieldViewFactory(Field field, String caption) {
		super(caption, field.name, field.getType(), field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * ���캯��
	 * @param field
	 * @param type
	 */
	public FieldViewFactory(Field field, ValidateType type) {
		super(field.caption, field.name, type, field.notNull,
				field.isPrimaryKey);
	}

	/**
	 * ���캯��
	 * @param field
	 * @param caption
	 * @param type
	 */
	public FieldViewFactory(Field field, String caption, ValidateType type) {
		super(caption, field.name, type, field.notNull, field.isPrimaryKey);
	}
	
	/**
	 * �޸�caption��colName��һ�����ڲ�ѯʱ��ʱ���ֶΣ�����ʼ���ں���ֹ����
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
	 * �����������
	 * 
	 * @param id
	 *            ���ID
	 * @param valueMap
	 *            model��view�������ݵ�map
	 * @param compParamMap
	 *            ���������ص�����map�����������������
	 * @param notNull
	 *            �Ƿ�����Ϊnull
	 * @return
	 */
	public Component createInputComp(String id, ValueMap valueMap,
			Map<String, Object> compParamMap, boolean notNull) {
		if (isReadonly()) {
			// ֻ����ֻ��ʾlabel
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
				// ����input���
				TextInput textInput = new TextInput(id, valueMap, this,
						notNull);
				addComponentAttributeValue(textInput.getFieldComponent());
				return textInput;
			}

		}
	}
	
	/**
	 * ����������
	 * 
	 * @param id
	 *            ���ID
	 * @param value
	 *            ��field������
	 * @return
	 */
	public Component createOutputComp(String id, Object value) {
		Label label = new Label(id, getFieldStringValue(value));
		label.setEscapeModelStrings(escapeModel);
		return label;
	}
	
	/**
	 * ���html����ֵ�����field��ȥ ���� new
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
	 * �ѻ�����Html������ӵ�Component��ȥ
	 * 
	 * @param comp
	 *            ��Ӧ��Component
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
	 * ��ȡFieldǰ��չʾ��ֵ��ʱ�伴��ʱ���ʽ��ʾ����Ҫת���ľͰ�ת����ת��
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
