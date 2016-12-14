package com.novel.entitys;

import java.io.Serializable;

/**
 * Created by runshu.lin on 16/11/29.
 */
public class NovelRules implements Serializable {

	private static final long serialVersionUID = 184247723801928334L;

	private Integer siteId;

	private String siteName;

	private String chapterCharset;

	private String chapterTitle;

	private String chapterUrl;

	private String chapterListSelector;

	private String detailTitleSelector;

	private String detailContentSelector;

	private String detailPrevSelector;

	private String detailNextSelector;

	private String novelSelector;

	private String novelNextPageSelector;

	private String novelNameSelector;

	private String novelDescSelector;

	private String novelAuthorSelector;

	private String novelTypeSelector;

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getChapterCharset() {
		return chapterCharset;
	}

	public void setChapterCharset(String chapterCharset) {
		this.chapterCharset = chapterCharset;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String getChapterUrl() {
		return chapterUrl;
	}

	public void setChapterUrl(String chapterUrl) {
		this.chapterUrl = chapterUrl;
	}

	public String getChapterListSelector() {
		return chapterListSelector;
	}

	public void setChapterListSelector(String chapterListSelector) {
		this.chapterListSelector = chapterListSelector;
	}

	public String getDetailTitleSelector() {
		return detailTitleSelector;
	}

	public void setDetailTitleSelector(String detailTitleSelector) {
		this.detailTitleSelector = detailTitleSelector;
	}

	public String getDetailContentSelector() {
		return detailContentSelector;
	}

	public void setDetailContentSelector(String detailContentSelector) {
		this.detailContentSelector = detailContentSelector;
	}

	public String getDetailPrevSelector() {
		return detailPrevSelector;
	}

	public void setDetailPrevSelector(String detailPrevSelector) {
		this.detailPrevSelector = detailPrevSelector;
	}

	public String getDetailNextSelector() {
		return detailNextSelector;
	}

	public void setDetailNextSelector(String detailNextSelector) {
		this.detailNextSelector = detailNextSelector;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getNovelSelector() {
		return novelSelector;
	}

	public void setNovelSelector(String novelSelector) {
		this.novelSelector = novelSelector;
	}

	public String getNovelNextPageSelector() {
		return novelNextPageSelector;
	}

	public void setNovelNextPageSelector(String novelNextPageSelector) {
		this.novelNextPageSelector = novelNextPageSelector;
	}

	public String getNovelNameSelector() {
		return novelNameSelector;
	}

	public void setNovelNameSelector(String novelNameSelector) {
		this.novelNameSelector = novelNameSelector;
	}

	public String getNovelDescSelector() {
		return novelDescSelector;
	}

	public void setNovelDescSelector(String novelDescSelector) {
		this.novelDescSelector = novelDescSelector;
	}

	public String getNovelAuthorSelector() {
		return novelAuthorSelector;
	}

	public void setNovelAuthorSelector(String novelAuthorSelector) {
		this.novelAuthorSelector = novelAuthorSelector;
	}

	public String getNovelTypeSelector() {
		return novelTypeSelector;
	}

	public void setNovelTypeSelector(String novelTypeSelector) {
		this.novelTypeSelector = novelTypeSelector;
	}
}
