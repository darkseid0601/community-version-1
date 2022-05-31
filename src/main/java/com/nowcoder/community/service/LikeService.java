package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.service
 * @CreateTime: 2022-05-31  11:24
 * @Description: 点赞业务
 */
@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: 点赞
     * @date: 2022/5/31 11:28
     * @param: [userId, entityType, entityId]
     * @return: void
     **/
    public void like(int userId, int entityType, int entityId, int entityUserId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisUtil.getUserLikeKey(entityUserId);

                Boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);
                // 开启事务
                operations.multi();
                if (isMember) {
                    redisTemplate.opsForSet().remove(entityLikeKey, userId);
                    redisTemplate.opsForValue().decrement(userLikeKey);
                } else {
                    redisTemplate.opsForSet().add(entityLikeKey, userId);
                    redisTemplate.opsForValue().increment(userLikeKey);
                }
                return operations.exec();
            }
        });
    }

    /**
     * @description: 查询某实体点赞的数量
     * @date: 2022/5/31 11:30
     * @param: [entityType, entityId]
     * @return: long
     **/
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * @description: 查询某人对某实体的点赞状态
     * @date: 2022/5/31 11:32
     * @param: [userId, entityType, entityId]
     * @return: int
     **/
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    /**
     * @description: 查询某个用户获得的赞
     * @date: 2022/5/31 16:44
     * @param: [userId]
     * @return: int
     **/
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }

}
