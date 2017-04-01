package com.flower.util;


/**
 * JavaScript 事件
 * 
 * 参考：  http://www.w3school.com.cn/jsref/jsref_events.asp
 * 
 * @author CorningSun
 *
 */
public enum JsEvent {
	
	//	                属性          当以下情况发生时，出现此事件
	onabort("onabort", "图像加载被中断"),
	onblur("onblur", "元素失去焦点"),
	onchange("onchange", "用户改变域的内容"),
	onclick("onclick", "鼠标点击某个对象"),
	ondblclick("ondblclick", "鼠标双击某个对象"),
	onerror("onerror", "当加载文档或图像时发生某个错误"),
	onfocus("onfocus", "元素获得焦点"),
	onkeydown("onkeydown", "某个键盘的键被按下"),
	onkeypress("onkeypress", "某个键盘的键被按下或按住"),
	onkeyup("onkeyup", "某个键盘的键被松开"),
	onload("onload", "某个页面或图像被完成加载"),
	onmousedown("onmousedown", "某个鼠标按键被按下"),
	onmousemove("onmousemove", "鼠标被移动"),
	onmouseout("onmouseout", "鼠标从某元素移开"),
	onmouseover("onmouseover", "鼠标被移到某元素之上"),
	onmouseup("onmouseup", "某个鼠标按键被松开"),
	onreset("onreset", "重置按钮被点击"),
	onresize("onresize", "窗口或框架被调整尺寸"),
	onselect("onselect", "文本被选定"),
	onsubmit("onsubmit", "提交按钮被点击"),
	onunload("onunload", "用户退出页面"),
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
