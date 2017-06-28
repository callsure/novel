package com.novel.mapper;

import com.novel.entitys.NovelList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelListMapper {
    int deleteByPrimaryKey(Integer listId);

    int insert(NovelList record);

    int insertSelective(NovelList record);

    NovelList selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(NovelList record);

    int updateByPrimaryKey(NovelList record);

    List<NovelList> findNovelListListsBySiteId(Integer siteId);
}