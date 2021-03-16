package com.phoenix.mybatispls.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    //Input自己输入ID，  UUID随机唯一值
    // ID_Wroker mp自带的, 数字类型的id
    //ID_worker_str 字符串
//    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    //自动填充
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    //自动填充
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //乐观锁; @Version注解  在插入时自动填充
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    //逻辑删除: select=false查询中排除此字段
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}
