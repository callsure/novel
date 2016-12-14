package com.novel.entitys;

import java.io.Serializable;

public class Nclass implements Serializable {

    private static final long serialVersionUID = -3210006069552247587L;

    private Integer nTypeId;

    private String nTypeName;

    private String nMapName;

    private Integer cStatus;

    public Integer getnTypeId() {
        return nTypeId;
    }

    public void setnTypeId(Integer nTypeId) {
        this.nTypeId = nTypeId;
    }

    public String getnTypeName() {
        return nTypeName;
    }

    public void setnTypeName(String nTypeName) {
        this.nTypeName = nTypeName == null ? null : nTypeName.trim();
    }

    public String getnMapName() {
        return nMapName;
    }

    public void setnMapName(String nMapName) {
        this.nMapName = nMapName;
    }

    public Integer getcStatus() {
        return cStatus;
    }

    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    @Override
    public String toString() {
        return "Nclass{" +
                "nTypeId=" + nTypeId +
                ", nTypeName='" + nTypeName + '\'' +
                ", nMapName='" + nMapName + '\'' +
                '}';
    }
}