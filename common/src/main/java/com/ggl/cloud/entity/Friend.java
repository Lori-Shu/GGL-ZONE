/*
*
*@Date:2022年5月27日
*
*@Author:Lori Shu
*
*/
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

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:25:09
 *
 */
@ApiModel(value = "Friend对象", description = "好友对象")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friend implements Serializable{
    private static final long serialVersionUID = 1L;
    @TableId(value="id",type =IdType.ASSIGN_UUID)
    private String id;
    private String userId;
    private String friendId;
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
