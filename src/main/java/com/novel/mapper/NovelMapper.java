package com.novel.mapper;

import com.novel.entitys.Novel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelMapper {
    int deleteByPrimaryKey(Integer novelId);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Integer novelId);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);

	/**
     * 批量插入数据
     * @param novels
     */
    void batchInsert(List<Novel> novels);

	/**
     * 删除数据,并重置标识
     */
    void deleteNovelAll();

	/**
     * 根据关键词搜索
     * @param keyword
     * @return
     */
    List<Novel> getsNovelByKeyword(String keyword);
}