package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.humzroy.framework.core.enums.DeleteEnums;

import java.util.List;

/**
  * ${table.comment!} 服务类
  *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

     /**
      * 分页查询
      *
      * @param conditions
      * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<${package.Entity}.${entity}>
      * @author ${author}
      * @date ${date}
      */
     Page<${entity}> getPage(${entity} conditions);

     /**
      * 列表查询
      *
      * @param conditions
      * @return java.util.List<${package.Entity}.${entity}>
      * @author ${author}
      * @date ${date}
      */
     List<${entity}> getList(${entity} conditions);

    /**
     * 单条查询
     *
     * @param conditions
     * @return ${package.Entity}.${entity}
     * @author ${author}
     * @date ${date}
     */
    ${entity} getOne(${entity} conditions);

    /**
     * 统计
     *
     * @param conditions
     * @return java.lang.Integer
     * @author ${author}
     * @date ${date}
     */
    Long getCount(${entity} conditions);
<#---->
<#--     /**-->
<#--      * 逻辑删除-->
<#--      *-->
<#--      * @param id-->
<#--      * @return boolean-->
<#--      * @author ${author}-->
<#--      * @date ${date}-->
<#--      */-->
<#--     default boolean removeLogicById(Long id) {-->
<#--          ${entity} record = new ${entity}();-->
<#--          record.setId(id);-->
<#--          record.setIsDeleted(DeleteEnums.DELETE.getIntKey());-->
<#--          return getBaseMapper().updateById(record) > 1;-->
<#--     }-->

}
</#if>
