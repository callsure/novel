package com.novel.entitys;

import java.io.Serializable;

public class Nclass implements Serializable {

    private static final long serialVersionUID = -3210006069552247587L;

    private Integer nTypeId;

    private String nTypeName;

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

    @Override
    public String toString() {
        return "Nclass{" +
                "nTypeId=" + nTypeId +
                ", nTypeName='" + nTypeName + '\'' +
                '}';
    }
}