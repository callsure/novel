package com.novel.beans;

import java.io.Serializable;

/**
 * Created by runshu.lin on 16/11/29.
 */
public class Chapter implements Serializable {

	private static final long serialVersionUID = -5884545572404236058L;

	private String title;

	private String url;

	private String novelName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	@Override
	public String toString() {
		return "Chapter{" +
				"title='" + title + '\'' +
				", url='" + url + '\'' +
				", novelName='" + novelName + '\'' +
				'}';
	}
}
