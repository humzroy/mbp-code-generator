package com.github.yizhen.generator.demo.high.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yizhen.generator.demo.high.dao.ISmsRecordMapper;
import com.github.yizhen.generator.demo.high.entity.SmsRecord;
import com.github.yizhen.generator.demo.high.service.ISmsRecordService;
import com.github.yizhen.framework.core.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * 短信发送记录 服务实现类
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
@Service
public class SmsRecordServiceImpl extends ServiceImpl<ISmsRecordMapper, SmsRecord> implements ISmsRecordService {

    /**
     * 分页查询
     *
     * @param conditions
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.github.yizhen.generator.demo.high.entity.SmsRecord>
     * @author wuhengzhen
     * @date 2023-02-08
     */
    @Override
    public Page<SmsRecord> getPage(SmsRecord conditions) {
        // 查询条件
        QueryWrapper<SmsRecord> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        // 组装分页参数
        Page<SmsRecord> page = new Page<>(conditions.getPageNo(), conditions.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 列表查询
     *
     * @param conditions
     * @return java.util.List<com.github.yizhen.generator.demo.high.entity.SmsRecord>
     * @author wuhengzhen
     * @date 2023-02-08
     */
    @Override
    public List<SmsRecord> getList(SmsRecord conditions) {
        // 查询条件
        QueryWrapper<SmsRecord> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.yizhen.generator.demo.high.entity.SmsRecord
     * @author wuhengzhen
     * @date 2023-02-08
     */
    @Override
    public SmsRecord getOne(SmsRecord conditions) {
        // 查询条件
        QueryWrapper<SmsRecord> queryWrapper = getQueryWrapper(conditions);
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
     * @date 2023-02-08
     */
    @Override
    public Long getCount(SmsRecord conditions) {
        // 查询条件
        QueryWrapper<SmsRecord> queryWrapper = getQueryWrapper(conditions);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 拼接查询条件
     *
     * @param conditions
     * @param conditions
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<com.github.yizhen.generator.demo.high.entity.SmsRecord>
     * @author wuhengzhen
     * @date 2023-02-08
     */
    private QueryWrapper<SmsRecord> getQueryWrapper(SmsRecord conditions) {
        QueryWrapper<SmsRecord> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<SmsRecord> lambda = queryWrapper.lambda();
        // 查询条件
        // 基本查询条件
        // 创建用户
        lambda.eq(StringUtils.isNotEmpty(conditions.getCreateBy()), SmsRecord::getCreateBy, conditions.getCreateBy());
        // 创建时间不为空
        if (ObjectUtils.isNotEmpty(conditions.getCreateTime())) {
            lambda.ge(SmsRecord::getCreateTime, DateUtil.getDayTime00(conditions.getCreateTime()));
            lambda.le(SmsRecord::getCreateTime, DateUtil.getDayTime59(conditions.getCreateTime()));
        }
        return queryWrapper;
    }
}
