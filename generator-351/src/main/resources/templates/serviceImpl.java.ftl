package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${superServiceImplClassPackage};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.github.yizhen.framework.core.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * ${table.comment!} 服务实现类
  *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

    /**
     * 分页查询
     *
     * @param conditions
     * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<${package.Entity}.${entity}>
     * @author ${author}
     * @date ${date}
     */
    @Override
    public Page<${entity}> getPage(${entity} conditions) {
        // 查询条件
        QueryWrapper<${entity}> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        // 组装分页参数
        Page<${entity}> page = new Page<>(conditions.getPageNo(), conditions.getPageSize());
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 列表查询
     *
     * @param conditions
     * @return java.util.List<${package.Entity}.${entity}>
     * @author ${author}
     * @date ${date}
     */
    @Override
    public List<${entity}> getList(${entity} conditions) {
        // 查询条件
        QueryWrapper<${entity}> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 单条查询
     *
     * @param conditions
     * @return ${package.Entity}.${entity}
     * @author ${author}
     * @date ${date}
     */
    @Override
    public ${entity} getOne(${entity} conditions) {
        // 查询条件
        QueryWrapper<${entity}> queryWrapper = getQueryWrapper(conditions);
        // 排序
        formatOrder(queryWrapper, conditions.getSort(), conditions.getOrder());
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author ${author}
     * @date ${date}
     */
    @Override
    public Long getCount(${entity} conditions) {
        // 查询条件
        QueryWrapper<${entity}> queryWrapper = getQueryWrapper(conditions);
        return baseMapper.selectCount(queryWrapper);
    }

    /**
     * 拼接查询条件
     *
     * @param conditions
     * @param conditions
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<${package.Entity}.${entity}>
     * @author ${author}
     * @date ${date}
     */
    private QueryWrapper<${entity}> getQueryWrapper(${entity} conditions) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<${entity}> lambda = queryWrapper.lambda();
        // 查询条件
    <#list table.fields as field>
        <#if 'create_by' == field.name>
        // 基本查询条件
        // 创建用户
        lambda.eq(StringUtils.isNotEmpty(conditions.getCreateBy()), ${entity}::getCreateBy, conditions.getCreateBy());
        <#elseif 'create_time' == field.name>
        // 创建时间不为空
        if (ObjectUtils.isNotEmpty(conditions.getCreateTime())) {
            lambda.ge(${entity}::getCreateTime, DateUtil.getDayTime00(conditions.getCreateTime()));
            lambda.le(${entity}::getCreateTime, DateUtil.getDayTime59(conditions.getCreateTime()));
        }
        <#elseif 'update_by' == field.name>
        // 更新用户
        lambda.eq(StringUtils.isNotEmpty(conditions.getUpdateBy()), ${entity}::getUpdateBy, conditions.getUpdateBy());
        <#elseif 'update_time' == field.name>
        // 更新时间不为空
        if (ObjectUtils.isNotEmpty(conditions.getUpdateTime())) {
            lambda.ge(${entity}::getUpdateTime, DateUtil.getDayTime00(conditions.getUpdateTime()));
            lambda.le(${entity}::getUpdateTime, DateUtil.getDayTime59(conditions.getUpdateTime()));
        }
        </#if>
    </#list>
        return queryWrapper;
    }
}
</#if>
