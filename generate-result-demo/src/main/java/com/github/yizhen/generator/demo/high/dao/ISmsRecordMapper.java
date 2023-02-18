package com.github.yizhen.generator.demo.high.dao;

import com.github.yizhen.generator.demo.high.entity.SmsRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
  * 短信发送记录 Mapper 接口
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
@Mapper
public interface ISmsRecordMapper extends BaseMapper<SmsRecord> {

}
