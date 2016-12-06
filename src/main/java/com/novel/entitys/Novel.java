package com.novel.entitys;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by runshu.lin on 16/12/3.
 */
public class Novel implements Serializable {

	private static final long serialVersionUID = -1579622037848075225L;
	private Integer novelId;
	/** 小说名字 */
	private String novelName;
	/** 小说作者 */
	private String author;
	/** 小说链接 */
	private String novelUrl;
	/** 小说类型 如:都市言情 */
	private String type;
	/** 小说最后章节 */
	private String lastUpdateChapter;
	/** 小说最后章节链接地址 */
	private String lastUpdateChapterUrl;
	/** 小说最后更新时间 */
	private Date lastUpdateTime;
	/** 小说状态 1:连载 2:完本 */
	private int status;
	/** 小说收录的时间 */
	private Date addTime;
	/** 小说名字第一个字母 */
	private char firstLetter;
	/** 小说平台id */
	private Integer platFormId;

	public Integer getNovelId() {
		return novelId;
	}

	public void setNovelId(Integer novelId) {
		this.novelId = novelId;
	}

	public String getNovelName() {
		return novelName;
	}

	public void setNovelName(String novelName) {
		this.novelName = novelName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNovelUrl() {
		return novelUrl;
	}

	public void setNovelUrl(String novelUrl) {
		this.novelUrl = novelUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLastUpdateChapter() {
		return lastUpdateChapter;
	}

	public void setLastUpdateChapter(String lastUpdateChapter) {
		this.lastUpdateChapter = lastUpdateChapter;
	}

	public String getLastUpdateChapterUrl() {
		return lastUpdateChapterUrl;
	}

	public void setLastUpdateChapterUrl(String lastUpdateChapterUrl) {
		this.lastUpdateChapterUrl = lastUpdateChapterUrl;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public char getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(char firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Integer getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(Integer platFormId) {
		this.platFormId = platFormId;
	}

	@Override
	public String toString() {
		return "Novel{" +
				"novelName='" + novelName + '\'' +
				", author='" + author + '\'' +
				", novelUrl='" + novelUrl + '\'' +
				", type='" + type + '\'' +
				", lastUpdateChapter='" + lastUpdateChapter + '\'' +
				", lastUpdateChapterUrl='" + lastUpdateChapterUrl + '\'' +
				", lastUpdateTime=" + lastUpdateTime +
				", status=" + status +
				", addTime=" + addTime +
				", firstLetter=" + firstLetter +
				", platFormId=" + platFormId +
				'}';
	}
}
