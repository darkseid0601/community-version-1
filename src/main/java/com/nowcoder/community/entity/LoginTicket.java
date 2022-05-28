package com.nowcoder.community.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-05-22  17:01
 * @Description: 用户凭证实体类
 */
@Data
@ToString
public class LoginTicket {
    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;
}
