package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.ManagedBean;
import java.util.List;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.dao
 * @CreateTime: 2022-05-20  18:25
 * @Description:
 */
@Mapper
@Repository
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit, int orderMode);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    // 发布帖子
    int insertDiscussPost(DiscussPost discussPost);

    // 根据帖子id查询帖子详情
    DiscussPost selectDiscussPostById(int id);

    // 更新帖子评论数
    int updateCommentCount(int id, int commentCount);

    // 更新帖子类型（置顶）
    int updateType(int id, int type);

    // 更新帖子状态（加精，删除）
    int updateStatus(int id, int status);

    // 更新帖子分数
    int updateScore(int id, double score);
}
