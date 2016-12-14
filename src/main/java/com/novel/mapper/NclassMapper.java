package com.novel.mapper;

import com.novel.entitys.Nclass;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NclassMapper {
    int deleteByPrimaryKey(Integer nTypeId);

    int insert(Nclass record);

    int insertSelective(Nclass record);

    Nclass selectByPrimaryKey(Integer nTypeId);

    int updateByPrimaryKeySelective(Nclass record);

    int updateByPrimaryKey(Nclass record);

    void updateNovelRulesType();

    List<Nclass> getsNclassAll();

    Nclass getNclassByTypeName(String typeName);
}