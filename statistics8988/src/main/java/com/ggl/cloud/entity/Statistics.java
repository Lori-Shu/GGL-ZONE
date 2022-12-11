package com.ggl.cloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>
 * 统计分析表
 * </p>
 *
 * @author baomidou
 * @since 2022-05-17
 */
// @ApiModel(value = "Statistics对象", description = "统计分析表")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Statistics implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // @ApiModelProperty("Create Time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // @ApiModelProperty("Update Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // @ApiModelProperty("deleted")
    @TableLogic
    private Boolean deleted;

    private Integer musicUploadCount;
    private Integer musicDeleteCount;
    private Integer userRegistryCount;
    @Version
    private Integer version;
}
