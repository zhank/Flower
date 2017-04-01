package com.flower.util;


/**
 * JavaScript �¼�
 * 
 * �ο���  http://www.w3school.com.cn/jsref/jsref_events.asp
 * 
 * @author CorningSun
 *
 */
public enum JsEvent {
	
	//	                ����          �������������ʱ�����ִ��¼�
	onabort("onabort", "ͼ����ر��ж�"),
	onblur("onblur", "Ԫ��ʧȥ����"),
	onchange("onchange", "�û��ı��������"),
	onclick("onclick", "�����ĳ������"),
	ondblclick("ondblclick", "���˫��ĳ������"),
	onerror("onerror", "�������ĵ���ͼ��ʱ����ĳ������"),
	onfocus("onfocus", "Ԫ�ػ�ý���"),
	onkeydown("onkeydown", "ĳ�����̵ļ�������"),
	onkeypress("onkeypress", "ĳ�����̵ļ������»�ס"),
	onkeyup("onkeyup", "ĳ�����̵ļ����ɿ�"),
	onload("onload", "ĳ��ҳ���ͼ����ɼ���"),
	onmousedown("onmousedown", "ĳ����갴��������"),
	onmousemove("onmousemove", "��걻�ƶ�"),
	onmouseout("onmouseout", "����ĳԪ���ƿ�"),
	onmouseover("onmouseover", "��걻�Ƶ�ĳԪ��֮��"),
	onmouseup("onmouseup", "ĳ����갴�����ɿ�"),
	onreset("onreset", "���ð�ť�����"),
	onresize("onresize", "���ڻ��ܱ������ߴ�"),
	onselect("onselect", "�ı���ѡ��"),
	onsubmit("onsubmit", "�ύ��ť�����"),
	onunload("onunload", "�û��˳�ҳ��"),
	;

	private String name;
	private String desc;

	private JsEvent(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

}
