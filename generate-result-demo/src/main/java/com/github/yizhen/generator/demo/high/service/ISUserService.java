package com.github.yizhen.generator.demo.high.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yizhen.framework.datasource.base.BaseService;
import com.github.yizhen.generator.demo.high.entity.SUser;

import java.util.List;

/**
  *  服务类
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
public interface ISUserService extends BaseService<SUser> {

     /**
      * 分页查询
      *
      * @param conditions
      * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.github.yizhen.entity.SUser>
      * @author wuhengzhen
      * @date 2023-02-08
      */
     Page<SUser> getPage(SUser conditions);

     /**
      * 列表查询
      *
      * @param conditions
      * @return java.util.List<com.github.yizhen.entity.SUser>
      * @author wuhengzhen
      * @date 2023-02-08
      */
     List<SUser> getList(SUser conditions);

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.yizhen.entity.SUser
     * @author wuhengzhen
     * @date 2023-02-08
     */
    SUser getOne(SUser conditions);

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author wuhengzhen
     * @date 2023-02-08
     */
    Long getCount(SUser conditions);

}
