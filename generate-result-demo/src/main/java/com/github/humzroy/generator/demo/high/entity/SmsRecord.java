package com.github.humzroy.generator.demo.high.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.humzroy.framework.datasource.base.BaseBean;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
  * sms_record : 短信发送记录
  *
 * @author wuhengzhen
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sms_record")
@ApiModel(value="SmsRecord对象", description="短信发送记录")
public class SmsRecord extends BaseBean {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "系统平台编号")
    @TableField("platform_code")
    private String platformCode;

    @ApiModelProperty(value = "接收短信的手机号码组，用半角逗号','分割")
    @TableField("phones")
    private String phones;

    @ApiModelProperty(value = "短信内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "短信类型 0正常短信 1验证码")
    @TableField("sms_type")
    private Integer smsType;

    @ApiModelProperty(value = "短信签名：【海尔】")
    @TableField("signature")
    private String signature;

    @ApiModelProperty(value = "信息批次号，供应商返回")
    @TableField("msg_id")
    private String msgId;

    @ApiModelProperty(value = "单条短信内容拆分条数")
    @TableField("split_count")
    private Integer splitCount;

    @ApiModelProperty(value = "是否发生成功 0否1是")
    @TableField("send_success")
    private Integer sendSuccess;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;


}
