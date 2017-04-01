package com.flower.db.field;

import org.apache.wicket.Component;

/**
 * 存储focusComp的结构，用于返回复合组件内部的输入焦点组件
 * 
 * @author renwei
 * @version 1.0
 * @since 1.0
 * @see {@link koal.wcl.inputs.InputLoop#populateItem}
 * @see {@link koal.wcl.field.FieldViewFactory#createInputComp}
 */
public class FocusComp {
	public Component focusComp;
}
