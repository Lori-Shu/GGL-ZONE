package com.ggl.cloud.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:25:27
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @ApiModel(value = "Video对象", description = "视频对象")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value="id",type =IdType.ASSIGN_UUID)
    private String id;
    private String videoName;
    private String videoAuthor;
    private String introduction;
    private String src;
    private String storePath;
    private String userId;
    @TableField(fill=FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    @TableField(fill=FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;
    @TableLogic
    private Boolean deleted;
    @Version
    private Integer version;
}
