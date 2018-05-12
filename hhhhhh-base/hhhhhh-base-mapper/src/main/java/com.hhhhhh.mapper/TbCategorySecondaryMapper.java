package com.hhhhhh.mapper;

import org.apache.ibatis.annotations.Param;
import com.hhhhhh.pojo.TbCategorySecondary;
import com.hhhhhh.pojo.TbCategorySecondaryExample;

import java.util.List;

public interface TbCategorySecondaryMapper {
    int countByExample(TbCategorySecondaryExample example);

    int deleteByExample(TbCategorySecondaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbCategorySecondary record);

    int insertSelective(TbCategorySecondary record);

    List<TbCategorySecondary> selectByExample(TbCategorySecondaryExample example);

    TbCategorySecondary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbCategorySecondary record, @Param("example") TbCategorySecondaryExample example);

    int updateByExample(@Param("record") TbCategorySecondary record, @Param("example") TbCategorySecondaryExample example);

    int updateByPrimaryKeySelective(TbCategorySecondary record);

    int updateByPrimaryKey(TbCategorySecondary record);
}