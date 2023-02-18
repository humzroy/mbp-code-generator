package com.github.yizhen.generator.demo.low.service;

import com.github.yizhen.framework.datasource.base.BaseService;
import com.github.yizhen.generator.demo.low.entity.SUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
  *  服务类
  *
 * @author wuhengzhen
 * @since 2023-02-07
 */
public interface ISUserService extends BaseService<SUser> {

     /**
      * 分页查询
      *
      * @param conditions
      * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.github.yizhen.demo.entity.SUser>
      * @author wuhengzhen
      * @date 2023-02-07
      */
     Page<SUser> getPage(SUser conditions);

     /**
      * 列表查询
      *
      * @param conditions
      * @return java.util.List<com.github.yizhen.demo.entity.SUser>
      * @author wuhengzhen
      * @date 2023-02-07
      */
     List<SUser> getList(SUser conditions);

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.yizhen.demo.entity.SUser
     * @author wuhengzhen
     * @date 2023-02-07
     */
    SUser getOne(SUser conditions);

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author wuhengzhen
     * @date 2023-02-07
     */
    Long getCount(SUser conditions);

}
