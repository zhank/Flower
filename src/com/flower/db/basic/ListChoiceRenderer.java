package com.flower.db.basic;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class ListChoiceRenderer<T> implements IChoiceRenderer<T> {

	private static final long serialVersionUID = 1L;

	private List<T> ranges;

	public ListChoiceRenderer(List<T> ranges) {
		super();
		this.ranges = ranges;
	}

	@Override
	public Object getDisplayValue(T object) {
		if (object instanceof Object[]) {
			Object[] ob = (Object[]) object;
			return ob[1];
		} else {
			return object;
		}
	}

	@Override
	public String getIdValue(T object, int index) {
		if (index >= 0) {
			if (object instanceof Object[]) {
				Object[] ob = (Object[]) object;
				if (ob[0] != null) {
					return ob[0].toString();
				}
			} else {
				T t = ranges.get(index);
				if (t != null) {
					return t.toString();
				}
			}
		}
		return null;
	}

	@Override
	public T getObject(String id, IModel<? extends List<? extends T>> choices) {
		List<? extends T> _choices = choices.getObject();
		for (int index = 0; index < _choices.size(); index++) {
			// Get next choice
			final T choice = _choices.get(index);
			if (getIdValue(choice, index).equals(id)) {
				return choice;
			}
		}
		return null;
	}

}
