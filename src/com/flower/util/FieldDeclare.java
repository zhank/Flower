package com.flower.util;

import java.io.Serializable;

import com.flower.db.field.Field;
import com.flower.db.field.FieldViewFactory;

public class FieldDeclare implements Serializable{
	private static final long serialVersionUID = 1L;
	private FieldViewFactory[] fvfs;
	private Field[] fields;

	public FieldDeclare(Field[] fields) {
		this.fields = fields;
		FieldViewFactory[] fvfs = new FieldViewFactory[fields.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field instanceof FieldViewFactory) {
				fvfs[i] = (FieldViewFactory) field;
			} else {
				fvfs[i] = new FieldViewFactory(field);
			}
		}
		this.fvfs = fvfs;
	}

	public FieldViewFactory[] getFvfArray() {
		return fvfs;
	}

	public Field[] getFieldArray() {
		return fields;
	}

	public int getFieldCount() {
		return fvfs.length;
	}
}
