package com.github.humzroy.generator.demo.low.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.humzroy.framework.core.api.ApiResult;
import com.github.humzroy.generator.demo.low.entity.SUser;
import com.github.humzroy.generator.demo.low.service.ISUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

/**
 * 前端控制器
 *
 * @author wuhengzhen
 * @since 2023-02-07
 */
@Api(value = "Controller", tags = "操作")
@RestController
@RequestMapping("/sUser")
public class SUserController {

    @Resource
    private ISUserService baseService;

    /**
     * 分页查询
     *
     * @param conditions
     * @return com.github.humzroy.framework.core.api.ApiResult
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @ApiOperation(value = "分页查询", tags = "操作")
    @ResponseBody
    @PostMapping("getPage")
    public ApiResult<Page<SUser>> getPage(@RequestBody SUser conditions) {
        Page<SUser> page = baseService.getPage(conditions);
        return ApiResult.ok(page);
    }

    /**
     * 列表查询
     *
     * @param conditions
     * @return com.github.humzroy.framework.core.api.ApiResult
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @ApiOperation(value = "列表查询", tags = "操作")
    @ResponseBody
    @PostMapping("getList")
    public ApiResult<List<SUser>> getList(@RequestBody SUser conditions) {
        List<SUser> list = baseService.getList(conditions);
        return ApiResult.ok(list);
    }

    /**
     * 单条查询
     *
     * @param conditions
     * @return com.github.humzroy.framework.core.api.ApiResult
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @ApiOperation(value = "单条查询", tags = "操作")
    @ResponseBody
    @PostMapping("getOne")
    public ApiResult<SUser> getOne(@RequestBody SUser conditions) {
        SUser record = baseService.getOne(conditions);
        return ApiResult.ok(record);
    }

    /**
     * 保存记录
     *
     * @param record
     * @return com.github.humzroy.framework.core.api.ApiResult
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @ApiOperation(value = "保存记录", tags = "操作")
    @ResponseBody
    @PostMapping("saveRecord")
    public ApiResult<SUser> saveRecord(@RequestBody SUser record) {
        if (baseService.saveOrUpdate(record)) {
            return ApiResult.ok(record, "保存成功");
        } else {
            return ApiResult.fail("保存失败");
        }
    }

    /**
     * 删除记录
     *
     * @param conditions
     * @return com.github.humzroy.framework.core.api.ApiResult
     * @author wuhengzhen
     * @date 2023-02-07
     */
    @ApiOperation(value = "删除记录", tags = "操作")
    @ResponseBody
    @PostMapping("deleteRecord")
    public ApiResult<Long> deleteRecord(@RequestBody SUser conditions) {
        if (baseService.removeById(conditions.getId())) {
            return ApiResult.ok(conditions.getId(), "删除成功");
        } else {
            return ApiResult.fail("删除失败");
        }
    }

}
