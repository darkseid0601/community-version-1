package com.nowcoder.community.service;

import com.nowcoder.community.dao.MessageMapper;
import com.nowcoder.community.entity.Message;
import com.nowcoder.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    private SensitiveFilter sensitiveFilter;

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

    /**
     * @description: 添加私信
     * @date: 2022/5/28 22:17
     * @param: [message]
     * @return: int
     **/
    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    /**
     * @description: 更新私信状态（已读）
     * @date: 2022/5/28 22:18
     * @param: [ids]
     * @return: int
     **/
    public int readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, 1);
    }

    /**
     * @description: 更新私信状态（删除）
     * @date: 2022/5/29 16:03
     * @param: [id]
     * @return: int
     **/
    public int deleteMessage(int id) {
        return messageMapper.updateDelStatus(id, 2);
    }

    /**
     * @description: 查询某个主题下最新的通知
     * @date: 2022/6/4 21:22
     * @param: [userId, topic]
     * @return: com.nowcoder.community.entity.Message
     **/
    public Message findLatestNotice(int userId, String topic) {
        return messageMapper.selectLatestNotice(userId, topic);
    }

    /**
     * @description: 查询某个主题所包含的通知数量
     * @date: 2022/6/4 21:22
     * @param: [userId, topic]
     * @return: int
     **/
    public int findNoticeCount(int userId, String topic) {
        return messageMapper.selectNoticeCount(userId, topic);
    }

    /**
     * @description: 查询未读的通知的数量
     * @date: 2022/6/4 21:22
     * @param: [userId, topic]
     * @return: int
     **/
    public int findNoticeUnreadCount(int userId, String topic) {
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    /**
     * @description: 查询某个主题所包含的通知列表
     * @date: 2022/6/4 21:23
     * @param: [userId, topic, offset, limit]
     * @return: java.util.List<com.nowcoder.community.entity.Message>
     **/
    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }
}
