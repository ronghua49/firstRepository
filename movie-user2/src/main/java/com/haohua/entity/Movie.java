package com.haohua.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Movie implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String movieName;
	private String directorName;
	private String area;
	private String year;
	private String imgName; // uuid 图片名
	private String content;
	private String simpleContent;
	private Timestamp createTime;// 根据sql当前事件更新
	private Integer scanNum;
	private Integer replyNum;
	private String remark;

	private List<Type> typelist;
	private String requestImgName;// 图片回显的 Http请求名
	//默认为系统时间
	private Timestamp lastReplyTimestamp = new Timestamp(System.currentTimeMillis());;

	public Timestamp getLastReplyTimestamp() {
		return lastReplyTimestamp;
	}

	public void setLastReplyTimestamp(Timestamp lastReplyTimestamp) {
		this.lastReplyTimestamp = lastReplyTimestamp;
	}

	public String getSimpleContent() {
		return simpleContent;
	}

	public void setSimpleContent(String simpleContent) {
		this.simpleContent = simpleContent;
	}

	public List<Type> getTypelist() {
		return typelist;
	}

	public String getRequestImgName() {
		return requestImgName;
	}

	public void setRequestImgName(String requestImgName) {
		this.requestImgName = requestImgName;
	}

	public void setTypelist(List<Type> typelist) {
		this.typelist = typelist;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreatTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getScanNum() {
		return scanNum;
	}

	public void setScanNum(Integer scanNum) {
		this.scanNum = scanNum;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
