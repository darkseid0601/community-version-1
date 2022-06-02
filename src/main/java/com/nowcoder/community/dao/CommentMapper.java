package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 根据实体选择评论
    List<Comment> selectCommentByEntity(int entityType, int entityId, int offset, int limit);

    // 统计评论数量
    int selectCountByEntity(int entityType, int entityId);

    // 评论
    int insertComment(Comment comment);

    // 根据id查询评论
    Comment selectCommentById(int id);

    // 根据用户id选择回复
    List<Comment> selectCommentByUser(int userId, int offset, int limit);

    // 统计某个用户回复数量
    int selectCountByUser(int userId);

}
