package com.nowcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-05-20  18:20
 * @Description:
 */
@Data
@ToString
public class DiscussPost {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private int commentCount;
    private double score;
}
