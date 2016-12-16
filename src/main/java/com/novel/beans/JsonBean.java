package com.novel.beans;

import java.io.Serializable;

public class JsonBean implements Serializable {

	private static final long serialVersionUID = -4712514419557396691L;

	private boolean success = false;

	private String msg = "失败";

	private Object obj = null;

	private String novelName = "";

	private String savePath = "";

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}
