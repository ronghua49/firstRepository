package com.haohua.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Type implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String text;
	private Timestamp createTime;
	private String remark;
	private boolean selected;
	

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
