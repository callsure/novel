package com.novel.mapper;

import com.novel.entitys.DownNovel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DownNovelMapper {
    int deleteByPrimaryKey(Integer dId);

    int insert(DownNovel record);

    int insertSelective(DownNovel record);

    DownNovel selectByPrimaryKey(Integer dId);

    int updateByPrimaryKeySelective(DownNovel record);

    int updateByPrimaryKey(DownNovel record);

    DownNovel getDownNovelByNId(Integer nId);

    DownNovel getDownNovelByNIdNow(Integer nId);

    List<DownNovel> getsFinishDownLoadNovel();
}