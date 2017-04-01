package com.flower.db.basic;

import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

public class MapChoiceRenderer<T, S> implements IChoiceRenderer<T> {

	private static final long serialVersionUID = 1L;

	private Map<T, S> ranges;
	private List<T> values;

	public MapChoiceRenderer(Map<T, S> ranges, List<T> values) {
		super();
		this.ranges = ranges;
		this.values = values;
	}

	@Override
	public Object getDisplayValue(T object) {
		return ranges.get(object);
	}

	@Override
	public String getIdValue(T object, int index) {
		if (index < 0) {
			return null;
		}
		T obj = values.get(index);
		if (null == obj) {
			return null;
		} else {
			return obj.toString();
		}
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
