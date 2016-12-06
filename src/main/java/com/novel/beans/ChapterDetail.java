package com.novel.beans;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by runshu.lin on 16/12/1.
 */
public class ChapterDetail implements Serializable {
	private static final long serialVersionUID = -7424211333685079537L;

	private String title;

	private String content;

	private String prev;

	private String next;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "ChapterDetail{" +
				"title='" + title + '\'' +
				", content='" + StringUtils.abbreviate(content,30) + '\'' +
				", prev='" + prev + '\'' +
				", next='" + next + '\'' +
				'}';
	}
}
