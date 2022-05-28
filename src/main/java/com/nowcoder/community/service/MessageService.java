package com.nowcoder.community.service;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.service
 * @CreateTime: 2022-05-28  18:17
 * @Description:
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * @description: 查询私信列表
     * @date: 2022/5/28 18:21
     * @param: [userId, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.Message>
     **/
    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    /**
     * @description: 查询私信总数
     * @date: 2022/5/28 18:22
     * @param: [userId]
     * @return: int
     **/
    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    /**
     * @description: 查询私信详情
     * @date: 2022/5/28 18:22
     * @param: [conversationId, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.Message>
     **/
    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    /**
     * @description: 查询一个私信里面的个数
     * @date: 2022/5/28 18:22
     * @param: [conversationId]
     * @return: int
     **/
    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    /**
     * @description: 查询未读私信个数
     * @date: 2022/5/28 18:24
     * @param: [userId, conversationId]
     * @return: int
     **/
    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }
}
