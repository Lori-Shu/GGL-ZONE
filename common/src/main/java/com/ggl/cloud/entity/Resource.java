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
import lombok.NoArgsConstructor;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author baomidou
 * @since 2022-07-05
 */
// @ApiModel(value = "Resource对象", description = "资源表")
@AllArgsConstructor
@NoArgsConstructor
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    // @ApiModelProperty("Primary Key")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    // @ApiModelProperty("Create Time")
    @TableField(fill = FieldFill.INSERT)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    // @ApiModelProperty("Update Time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    // @ApiModelProperty("deleted")
    @TableLogic
    private Boolean deleted;

    // @ApiModelProperty("version")
    @Version
    private Integer version;

    // @ApiModelProperty("resource_name")
    private String resourceName;

    // @ApiModelProperty("type")
    private String resourceType;

    // @ApiModelProperty("request_type")
    private String requestType;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Resource{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", deleted=" + deleted +
        ", version=" + version +
        ", resourceName=" + resourceName +
        ", resourceType=" + resourceType +
        ", requestType=" + requestType +
        "}";
    }
}
