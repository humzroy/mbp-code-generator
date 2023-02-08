package com.github.humzroy.generator.demo.high.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.humzroy.generator.demo.high.entity.SUser;
import org.apache.ibatis.annotations.Mapper;

/**
  *  Mapper 接口
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
@Mapper
public interface ISUserMapper extends BaseMapper<SUser> {

}
