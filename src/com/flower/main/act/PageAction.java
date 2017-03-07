package com.flower.main.act;

import org.apache.wicket.Page;

import com.flower.main.page.AppPage;

/**
 * Ä£¿é²Ù×÷Àà
 *
 */
public abstract class PageAction implements IPageAction {
	private static final long serialVersionUID = 1L;

	@Override
	final public void exec(final Page page, final Object param) {
		AppPage pg = (AppPage) page;
		try {
			run(pg, param);
		} catch (Exception e) {
			pg.toError(e);
		}

	}

	abstract public void run(final AppPage page, final Object param) throws Exception;
}
