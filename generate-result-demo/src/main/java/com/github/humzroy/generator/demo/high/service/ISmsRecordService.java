package com.github.humzroy.generator.demo.high.service;

import com.github.humzroy.generator.demo.high.entity.SmsRecord;
import com.github.humzroy.framework.datasource.base.BaseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.humzroy.framework.core.enums.DeleteEnums;

import java.util.List;

/**
  * 短信发送记录 服务类
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
public interface ISmsRecordService extends BaseService<SmsRecord> {

     /**
      * 分页查询
      *
      * @param conditions
      * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.github.humzroy.generator.demo.high.entity.SmsRecord>
      * @author wuhengzhen
      * @date 2023-02-08
      */
     Page<SmsRecord> getPage(SmsRecord conditions);

     /**
      * 列表查询
      *
      * @param conditions
      * @return java.util.List<com.github.humzroy.generator.demo.high.entity.SmsRecord>
      * @author wuhengzhen
      * @date 2023-02-08
      */
     List<SmsRecord> getList(SmsRecord conditions);

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.humzroy.generator.demo.high.entity.SmsRecord
     * @author wuhengzhen
     * @date 2023-02-08
     */
    SmsRecord getOne(SmsRecord conditions);

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author wuhengzhen
     * @date 2023-02-08
     */
    Long getCount(SmsRecord conditions);

}
