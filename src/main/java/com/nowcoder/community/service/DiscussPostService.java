package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @BelongsProject: community
 * @BelongsPackage: com.nowcoder.community.service
 * @CreateTime: 2022-05-19  18:25
 * @Description: 帖子业务
 */
@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * @description: 显示帖子
     * @date: 2022/5/26 10:32
     * @param: [userId, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.DiscussPost>
     **/
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    /**
     * @description: 帖子行数
     * @date: 2022/5/26 10:32
     * @param: [userId]
     * @return: int
     **/
    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    /**
     * @description: 发布帖子
     * @date: 2022/5/26 10:37
     * @param: [post]
     * @return: int
     **/
    public int addDiscussPost(DiscussPost post) {
        // 这种判断方式无效, 在Controller里判断
//        if (post == null) {
//            throw new IllegalArgumentException("参数不能为空!");
//        }
        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

        return discussPostMapper.insertDiscussPost(post);
    }

    /**
     * @description: 查询帖子详情
     * @date: 2022/5/26 14:31
     * @param: [id]
     * @return: com.nowcoder.community.entity.DiscussPost
     **/
    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    /**
     * @description: 更新评论数
     * @date: 2022/5/27 16:39
     * @param: [id, commentCount]
     * @return: int
     **/
    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    /**
     * @description: 更新帖子类型（置顶）
     * @date: 2022/6/13 22:35
     * @param: [id, type]
     * @return: int
     **/
    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }

}
