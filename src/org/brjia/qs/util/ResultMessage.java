package org.brjia.qs.util;

public class ResultMessage {

	
	private String msg;
	private Object data;
	
	public ResultMessage(){
	}
	public ResultMessage(String msg){
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
