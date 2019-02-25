package com.haohua.util;

public class Reqresult {
	private static final String STATE_SUCCESS = "success";
	private static final String STATE_ERROR = "error";
	private String state;
	private Object data;
	private String message;

	public static Reqresult success() {
		// 发送时只有成功信息
		Reqresult reqresult = new Reqresult();
		reqresult.setState(STATE_SUCCESS);
		return reqresult;
	}

	public static Reqresult success(Object object) {
		// 发送成功信息和成功对象
		Reqresult reqresult = new Reqresult();
		reqresult.setState(STATE_SUCCESS);
		reqresult.setData(object);
		return reqresult;
	}

	public static Reqresult error(String message) {
		// 发送错误信息和错误的原因提示
		Reqresult reqresult = new Reqresult();
		reqresult.setState(STATE_ERROR);
		reqresult.setMessage(message);
		return reqresult;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
