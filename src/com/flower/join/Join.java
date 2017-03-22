package com.flower.join;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * 连接多个通用接口的对象
 * @author renwei
 * @version 0.9
 * @since 0.9
 */
public class Join extends Panel {
	private static final long serialVersionUID = 1L;
	
	private Component[] comps;
	
	public Join(String id, final Component comp1, final Component comp2) {
		this(id, new Component[] {comp1, comp2});
	}
		
	public Join(String id, final Component comp1, final Component comp2, final Component comp3) {
		this(id, new Component[] {comp1, comp2, comp3});
	}
	
    public Join(String id, final Component[] comps) {
        super(id);
        this.comps = comps;
        add(new JoinLoop("joins", comps));
    }
    
    public static final String getJoinID() {
    	return "ji";
    }
    
	private static class JoinLoop extends Loop {
		private static final long serialVersionUID = 1L;
		
		private final Component[] m_comps;
		public JoinLoop(String id, final Component[] comps) {
			super(id, comps.length);
			m_comps = comps;
		}
		
		protected void populateItem(LoopItem item)
		{
			item.add(m_comps[item.getIndex()]);
		}
	}
	
	public Component[] getComps() {
		return comps;
	}
}
