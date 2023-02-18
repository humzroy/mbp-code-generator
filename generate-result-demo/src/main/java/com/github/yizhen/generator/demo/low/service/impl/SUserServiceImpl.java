package com.github.yizhen.generator.demo.low.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yizhen.framework.core.util.DateUtil;
import com.github.yizhen.generator.demo.low.dao.SUserMapper;
import com.github.yizhen.generator.demo.low.entity.SUser;
import com.github.yizhen.generator.demo.low.service.ISUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  *  服务实现类
  *
 * @author wuhengzhen
 * @since 2023-02-07
 */
@Service
public class SUserServiceImpl extends ServiceImpl<SUserMapper, SUser> implements ISUserService {

    /**
     * 分页查询
     *
     * @param conditions
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.github.yizhen.demo.entity.SUser>
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @Override
    public Page<SUser> getPage(SUser conditions) {
        // 查询条件
        QueryWrapper<SUser> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        // 组装分页参数
        Page<SUser> page = new Page<>(conditions.getPageNo(), conditions.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 列表查询
     *
     * @param conditions
     * @return java.util.List<com.github.yizhen.demo.entity.SUser>
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @Override
    public List<SUser> getList(SUser conditions) {
        // 查询条件
        QueryWrapper<SUser> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.yizhen.demo.entity.SUser
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @Override
    public SUser getOne(SUser conditions) {
        // 查询条件
        QueryWrapper<SUser> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @Override
    public Long getCount(SUser conditions) {
        // 查询条件
        QueryWrapper<SUser> queryWrapper = getQueryWrapper(conditions);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 拼接查询条件
     *
     * @param conditions
     * @param conditions
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.github.yizhen.demo.entity.SUser>
     * @author wuhengzhen
     * @date 2023-02-07
     */
    private QueryWrapper<SUser> getQueryWrapper(SUser conditions) {
        QueryWrapper<SUser> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SUser> lambda = queryWrapper.lambda();
        // 查询条件
        // 创建时间不为空
        if (ObjectUtils.isNotEmpty(conditions.getCreateTime())) {
            lambda.ge(SUser::getCreateTime, DateUtil.getDayTime00(conditions.getCreateTime()));
            lambda.le(SUser::getCreateTime, DateUtil.getDayTime59(conditions.getCreateTime()));
        }
        // 基本查询条件
        // 创建用户
        lambda.eq(StringUtils.isNotEmpty(conditions.getCreateBy()), SUser::getCreateBy, conditions.getCreateBy());
        // 更新用户
        lambda.eq(StringUtils.isNotEmpty(conditions.getUpdateBy()), SUser::getUpdateBy, conditions.getUpdateBy());
        return queryWrapper;
    }
}
