package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yizhen.framework.mybatis.base.BaseController;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import com.github.yizhen.framework.core.api.ApiResult;
<#if swagger>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
  * ${table.comment!} 前端控制器
  *
 * @author ${author}
 * @since ${date}
 */
<#if swagger>
@Api(value="${table.comment!}Controller", tags="操作${table.comment!}")
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} baseService;

    /**
     * 分页查询
     *
     * @param conditions
     * @return com.github.yizhen.framework.core.api.ApiResult
     * @author ${author}
     * @date ${date}
     */
<#if swagger>
    @ApiOperation(value = "分页查询", tags = "操作${table.comment!}")
</#if>
    @ResponseBody
    @PostMapping("getPage")
    public ApiResult<Page<${entity}>> getPage(@RequestBody ${entity} conditions) {
        Page<${entity}> page = baseService.getPage(conditions);
        return ApiResult.ok(page);
    }
    
    /**
     * 列表查询
     *
     * @param conditions
     * @return com.github.yizhen.framework.core.api.ApiResult
     * @author ${author}
     * @date ${date}
     */
<#if swagger>
    @ApiOperation(value = "列表查询", tags = "操作${table.comment!}")
</#if>
    @ResponseBody
    @PostMapping("getList")
    public ApiResult<List<${entity}>> getList(@RequestBody ${entity} conditions) {
        List<${entity}> list = baseService.getList(conditions);
        return ApiResult.ok(list);
    }

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.yizhen.framework.core.api.ApiResult
     * @author ${author}
     * @date ${date}
     */
<#if swagger>
    @ApiOperation(value = "单条查询", tags = "操作${table.comment!}")
</#if>
    @ResponseBody
    @PostMapping("getOne")
    public ApiResult<${entity}> getOne(@RequestBody ${entity} conditions) {
        ${entity} record = baseService.getOne(conditions);
        return ApiResult.ok(record);
    }

    /**
     * 保存记录
     *
     * @param record
     * @return com.github.yizhen.framework.core.api.ApiResult
     * @author ${author}
     * @date ${date}
     */
<#if swagger>
    @ApiOperation(value = "保存记录", tags = "操作${table.comment!}")
</#if>
    @ResponseBody
    @PostMapping("saveRecord")
    public ApiResult<${entity}> saveRecord(@RequestBody ${entity} record) {
        if (baseService.saveOrUpdate(record)){
            return ApiResult.ok(record, "保存成功");
        } else {
            return ApiResult.fail("保存失败");
        }
    }

    /**
     * 删除记录
     *
     * @param conditions
     * @return com.github.yizhen.framework.core.api.ApiResult
     * @author ${author}
     * @date ${date}
     */
<#if swagger>
    @ApiOperation(value = "删除记录", tags = "操作${table.comment!}")
</#if>
    @ResponseBody
    @PostMapping("deleteRecord")
    public ApiResult<Long> deleteRecord(@RequestBody ${entity} conditions) {
        if(baseService.removeById(conditions.getId())) {
            return ApiResult.ok(conditions.getId(), "删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }
<#---->
<#--    /**-->
<#--     * 逻辑删除记录-->
<#--     *-->
<#--     * @param id-->
<#--     * @return com.github.yizhen.framework.core.api.ApiResult-->
<#--     * @author ${author}-->
<#--     * @date ${date}-->
<#--     */-->
<#--    @ResponseBody-->
<#--    @PostMapping("deleteLogicRecord")-->
<#--    public ApiResult<?> deleteLogicRecord(@RequestParam Long id) {-->
<#--        if(baseService.removeLogicById(id)) {-->
<#--            return ApiResult.ok(id, "删除成功");-->
<#--        } else {-->
<#--            return ApiResult.fail("删除失败");-->
<#--        }-->
<#--    }-->

}
</#if>
