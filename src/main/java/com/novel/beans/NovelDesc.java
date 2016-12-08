package com.novel.beans;

import java.io.Serializable;

/**
 * Created by runshu.lin on 16/12/7.
 */
public class NovelDesc implements Serializable {
	private static final long serialVersionUID = -2600637508764784892L;

	private Integer siteId;

	private String novelName;

	private String novelAuthor;

	private String desc;

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getNovelAuthor() {
		return novelAuthor;
	}

	public void setNovelAuthor(String novelAuthor) {
		this.novelAuthor = novelAuthor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Override
	public String toString() {
		return "NovelDesc{" +
				"siteId=" + siteId +
				", novelName='" + novelName + '\'' +
				", novelAuthor='" + novelAuthor + '\'' +
				", desc='" + desc + '\'' +
				'}';
	}
}
