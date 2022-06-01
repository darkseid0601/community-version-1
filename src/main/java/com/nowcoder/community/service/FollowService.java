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
 * @CreateTime: 2022-06-01  10:15
 * @Description: 关注业务
 */
@Service
public class FollowService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: 关注
     * @date: 2022/6/1 10:35
     * @param: [userId, entityType, entityId]
     * @return: void
     **/
    public void follow(int userId, int entityType, int entityId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisUtil.getFollowerKey(entityType, entityId);

                operations.multi();

                operations.opsForZSet().add(followeeKey, entityId, System.currentTimeMillis());
                operations.opsForZSet().add(followerKey, userId, System.currentTimeMillis());

                return operations.exec();
            }
        });
    }
    /**
     * @description: 取关
     * @date: 2022/6/1 10:36
     * @param: [userId, entityType, entityId]
     * @return: void
     **/
    public void unfollow(int userId, int entityType, int entityId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followeeKey = RedisUtil.getFolloweeKey(userId, entityType);
                String followerKey = RedisUtil.getFollowerKey(entityType, entityId);

                operations.multi();

                operations.opsForZSet().remove(followeeKey, entityId);
                operations.opsForZSet().remove(followerKey, userId);

                return operations.exec();
            }
        });
    }

    /**
     * @description: 查询关注的实体的数量
     * @date: 2022/6/1 10:37
     * @param: [userId, entityType]
     * @return: long
     **/
    public long findFolloweeCount(int userId, int entityType) {
        String followeeKey = RedisUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }

    /**
     * @description: 查询实体的粉丝的数量
     * @date: 2022/6/1 10:40
     * @param: [entityType, entityId]
     * @return: long
     **/
    public long findFollowerCount(int entityType, int entityId) {
        String followerKey = RedisUtil.getFollowerKey(entityType, entityId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }

    /**
     * @description: 查询当前用户是否已关注该实体
     * @date: 2022/6/1 10:48
     * @param: [userId, entityType, entityId]
     * @return: boolean
     **/
    public boolean isFollowed(int userId, int entityType, int entityId) {
        String followeeKey = RedisUtil.getFolloweeKey(userId, entityType);
        return redisTemplate.opsForZSet().score(followeeKey, entityId) != null;
    }
}
