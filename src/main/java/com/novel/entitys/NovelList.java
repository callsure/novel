package com.novel.entitys;

public class NovelList {
    private Integer listId;

    private String listMap;

    private String listUrl;

    private Integer siteId;

    private String siteName;

    private String siteChinaName;

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListMap() {
        return listMap;
    }

    public void setListMap(String listMap) {
        this.listMap = listMap == null ? null : listMap.trim();
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl == null ? null : listUrl.trim();
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName == null ? null : siteName.trim();
    }

    public String getSiteChinaName() {
        return siteChinaName;
    }

    public void setSiteChinaName(String siteChinaName) {
        this.siteChinaName = siteChinaName == null ? null : siteChinaName.trim();
    }
}