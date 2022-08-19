package com.ggl.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:24:51
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonMessage {
    private String target;
    private String message;
    private String from;
    private String createTime;
}
