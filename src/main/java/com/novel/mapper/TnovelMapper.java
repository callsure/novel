package com.novel.mapper;

import com.novel.beans.NovelDesc;
import com.novel.entitys.Tnovel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TnovelMapper {
    int deleteByPrimaryKey(Integer nId);

    int insert(Tnovel record);

    int insertSelective(Tnovel record);

    Tnovel selectByPrimaryKey(Integer nId);

    int updateByPrimaryKeySelective(Tnovel record);

    int updateByPrimaryKey(Tnovel record);

    List<Tnovel> getsNovelByType(String type);

    List<Tnovel> getsNovelAll();

    void updateByNameAndAuthor(NovelDesc novelDesc);

    void updateNovelChapter();

    List<Tnovel> getHotNovelAllType(@Param("siteId") Integer siteId, @Param("typeId") Integer typeId);

    List<Tnovel> findNovelByQuery(String query);

}