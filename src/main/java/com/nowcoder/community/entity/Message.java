package com.nowcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-05-28  17:52
 * @Description: 私信实体类
 */
@Data
@ToString
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;
}
