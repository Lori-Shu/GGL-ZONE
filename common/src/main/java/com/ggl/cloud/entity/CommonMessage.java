package com.ggl.cloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonMessage {
    private String target;
    private String message;
    private String from;
    private String createTime;
}
