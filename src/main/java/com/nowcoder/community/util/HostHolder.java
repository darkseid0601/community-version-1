package com.nowcoder.community.util;

import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.util
 * @CreateTime: 2022-05-22  21:58
 * @Description:
 */
@Component
public class HostHolder {

    private ThreadLocal<User> hostHolder = new ThreadLocal<>();

    public void setUser(User user) {
        hostHolder.set(user);
    }

    public User getUser() {
        return hostHolder.get();
    }

    public void  clear() {
        hostHolder.remove();
    }
}
