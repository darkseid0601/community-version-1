package com.nowcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-05-27  11:14
 * @Description: 评论实体类
 */
@Data
@ToString
public class Comment {
    private int id;
    private int userId;
    private int entityType;
    private int entityId;
    private int targetId;
    private String content;
    private int status;
    private Date createTime;
}
