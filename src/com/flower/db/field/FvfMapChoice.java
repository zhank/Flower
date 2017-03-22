package com.flower.db.field;

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.util.value.ValueMap;

import com.flower.db.basic.ChoiceInput;

/**
 * {@link koal.wcl.basic.ChoiceInput ChoiceInput} ����Ĵ���������������Map���ͣ���֧��"id<->����"
 * ��ʽ�����ID����Ϊ����
 */
public class FvfMapChoice extends FieldViewFactory {
	private static final long serialVersionUID = 1L;

	public FvfMapChoice(Field field) {
		super(field);
	}

	/**
	 * ����{@link koal.wcl.basic.ChoiceInput ChoiceInput}���
	 */
	@SuppressWarnings("unchecked")
	public Component createInputComp(String id, ValueMap valueMap, Map<String, Object> compParamMap, boolean notNull,
			FocusComp focusComp) {
		Map<Object, Object> ranges = null;
		if (compParamMap != null) {
			ranges = (Map<Object, Object>) compParamMap.get(this.name);
		} else {
			ranges = new HashMap<Object, Object>();
		}
		ChoiceInput selectInput = new ChoiceInput(id, valueMap, ranges, this, notNull);
		if (focusComp != null) {
			focusComp.focusComp = selectInput.getFieldComponent();
		}
		addComponentAttributeValue(selectInput.getFieldComponent());
		addComponentCssStyle(selectInput.getFieldComponent());
		return selectInput;
	}
}
