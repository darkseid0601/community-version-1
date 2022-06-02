package com.nowcoder.community.service;

import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.service
 * @CreateTime: 2022-05-27  11:23
 * @Description: 评论业务
 */
@Service
public class CommentService implements CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    /**
     * @description: 根据实体选择评论
     * @date: 2022/5/27 16:41
     * @param: [entityType, entityId, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.Comment>
     **/
    public List<Comment> findCommentsByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentByEntity(entityType, entityId, offset, limit);
    }

    /**
     * @description: 统计评论数量
     * @date: 2022/5/27 16:41
     * @param: [entityType, entityId]
     * @return: int
     **/
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    /**
     * @description: 评论
     * @date: 2022/5/27 16:41
     * @param: [comment]
     * @return: int
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addComment(Comment comment) {
        if(comment == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }

//        添加下一行代码会报错
//        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));

        // 添加评论
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        int rows = commentMapper.insertComment(comment);

        // 更新帖子评论数量
        if(comment.getEntityType() == ENTITY_TYPE_POST) {
            int count = commentMapper.selectCountByEntity(comment.getEntityType(), comment.getEntityId());
            discussPostService.updateCommentCount(comment.getEntityId(), count);
        }
        return rows;
    }

    /**
     * @description: 根据id查询评论
     * @date: 2022/6/2 9:39
     * @param: [id]
     * @return: com.nowcoder.community.entity.Comment
     **/
    public Comment findCommentById(int id) {
        return commentMapper.selectCommentById(id);
    }

    /**
     * @description: 查询某用户的回复
     * @date: 2022/6/2 9:41
     * @param: [userId, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.Comment>
     **/
    public List<Comment> findUserComments(int userId, int offset, int limit) {
        return commentMapper.selectCommentByUser(userId, offset, limit);
    }

    /**
     * @description: 查询某用户回复数量
     * @date: 2022/6/2 9:44
     * @param: [userId]
     * @return: int
     **/
    public int findUserCount(int userId) {
        return commentMapper.selectCountByUser(userId);
    } 

}
