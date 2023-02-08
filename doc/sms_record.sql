CREATE TABLE `sms_record`
(
    `id`            bigint(10)   NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `platform_code` varchar(90)           DEFAULT NULL COMMENT '系统平台编号',
    `phones`        varchar(255) NOT NULL COMMENT '接收短信的手机号码组，用半角逗号'',''分割',
    `content`       varchar(255) NOT NULL COMMENT '短信内容',
    `sms_type`      tinyint(2)   NOT NULL DEFAULT '0' COMMENT '短信类型 0正常短信 1验证码',
    `signature`     varchar(50)           DEFAULT NULL COMMENT '短信签名：【阿里云】',
    `msg_id`        varchar(32)           DEFAULT NULL COMMENT '信息批次号，供应商返回',
    `split_count`   int(10)               DEFAULT NULL COMMENT '单条短信内容拆分条数',
    `send_success`  tinyint(2)   NOT NULL DEFAULT '0' COMMENT '是否发生成功 0否1是',
    `create_by`     varchar(50)           DEFAULT NULL COMMENT '创建人',
    `create_time`   datetime              DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1 DEFAULT C