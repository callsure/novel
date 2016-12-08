package com.novel.entitys;

import java.io.Serializable;
import java.util.Date;

public class Tnovel implements Serializable {

    private static final long serialVersionUID = 3096555456186315565L;

    private Integer nId;

    private String nName;

    private String nAuthor;

    private String nUrl;

    private String nType;

    private String nDesc;

    private Date nAddTime;

    private Integer siteId;

    private Integer nStatus;

    private String nLastUpdateChapter;

    private String nLastUpdateChapterUrl;

    private Date nLastUpdateTime;

    private Integer nBrowse;

    private String firstLetter;

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName == null ? null : nName.trim();
    }

    public String getnAuthor() {
        return nAuthor;
    }

    public void setnAuthor(String nAuthor) {
        this.nAuthor = nAuthor == null ? null : nAuthor.trim();
    }

    public String getnUrl() {
        return nUrl;
    }

    public void setnUrl(String nUrl) {
        this.nUrl = nUrl == null ? null : nUrl.trim();
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getnDesc() {
        return nDesc;
    }

    public void setnDesc(String nDesc) {
        this.nDesc = nDesc == null ? null : nDesc.trim();
    }

    public Date getnAddTime() {
        return nAddTime;
    }

    public void setnAddTime(Date nAddTime) {
        this.nAddTime = nAddTime;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getnStatus() {
        return nStatus;
    }

    public void setnStatus(Integer nStatus) {
        this.nStatus = nStatus;
    }

    public String getnLastUpdateChapter() {
        return nLastUpdateChapter;
    }

    public void setnLastUpdateChapter(String nLastUpdateChapter) {
        this.nLastUpdateChapter = nLastUpdateChapter == null ? null : nLastUpdateChapter.trim();
    }

    public String getnLastUpdateChapterUrl() {
        return nLastUpdateChapterUrl;
    }

    public void setnLastUpdateChapterUrl(String nLastUpdateChapterUrl) {
        this.nLastUpdateChapterUrl = nLastUpdateChapterUrl == null ? null : nLastUpdateChapterUrl.trim();
    }

    public Date getnLastUpdateTime() {
        return nLastUpdateTime;
    }

    public void setnLastUpdateTime(Date nLastUpdateTime) {
        this.nLastUpdateTime = nLastUpdateTime;
    }

    public Integer getnBrowse() {
        return nBrowse;
    }

    public void setnBrowse(Integer nBrowse) {
        this.nBrowse = nBrowse;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter == null ? null : firstLetter.trim();
    }
}