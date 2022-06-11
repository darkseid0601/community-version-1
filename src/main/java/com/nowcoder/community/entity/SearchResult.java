package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.entity
 * @CreateTime: 2022-06-11  14:19
 * @Description: 自定义实体类，用于暂存ES中查询到的列表和总行数
 */
@Data
@AllArgsConstructor
public class SearchResult {

    private List<DiscussPost> list;
    private long total;

}
