package com.nowcoder.community.service;

import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.service
 * @CreateTime: 2022-05-20  18:28
 * @Description: 用户业务
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

//    @Autowired
//    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public User findUserById(int id) {
//        return userMapper.selectById(id);
        User user = getCache(id);
        if(user == null) {
            user = initCache(id);
        }
        return user;
    }

    /**
     * @description: 注册
     * @date: 2022/5/24 21:17
     * @param: [user]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> register(User user) {
        Map<String ,Object> map = new HashMap<>();

        if(user == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        if(StringUtils.isBlank(user.getUsername())) {
            map.put("usernameMsg", "帐号不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空！");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空！");
            return map;
        }
        // 验证账号
        User u = userMapper.selectByName(user.getUsername());
        if(u != null) {
            map.put("usernameMsg", "该账号已存在！");
            return map;
        }
        // 验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "该邮箱已被注册！");
            return map;
        }

        // 注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // http://localhost:8080/community/activation/101/code
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    /**
     * @description: 判断用户是否激活
     * @date: 2022/5/24 21:18
     * @param: [userId, code]
     * @return: int
     **/
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if(user.getStatus() == 1) {
            return ACTIVATION_REPEAT;
        } else if(user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            clearCache(userId);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }

    }

    /**
     * @description: 登录
     * @date: 2022/5/24 21:17
     * @param: [username, password, expiredSeconds]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> login(String username, String password, long expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(username)) {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if(StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByName(username);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }
        // 验证状态
        if (user.getStatus() == 0) {
            map.put("usernameMsg", "该账号未激活!");
            return map;
        }
        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }
        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
//        loginTicketMapper.insertLoginTicket(loginTicket);

        String redisKey = RedisUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
//        loginTicketMapper.updateStatus(ticket, 1);
        String redisKey = RedisUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }

    /**
     * @description: 寻找用户凭证
     * @date: 2022/5/24 21:19
     * @param: [ticket]
     * @return: com.nowcoder.community.entity.LoginTicket
     **/
    public LoginTicket findLoginTicket(String ticket) {
//        return loginTicketMapper.selectByTicket(ticket);
        String redisKey = RedisUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * @description: 更新头像
     * @date: 2022/5/24 21:17
     * @param: [userId, headerUrl]
     * @return: int
     **/
    public int updateHeader(int userId, String headerUrl) {
//        return userMapper.updateHeader(userId, headerUrl);
        int rows = userMapper.updateHeader(userId, headerUrl);
        clearCache(userId);
        return rows;
    }

    /**
     * @description: 重置密码
     * @date: 2022/5/24 21:16
     * @param: [email, password]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> resetPassword(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if(StringUtils.isBlank(email)) {
            map.put("emailMsg", "邮箱为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码为空！");
            return map;
        }
        // 验证邮箱
        User user = userMapper.selectByEmail(email);
        if(user == null) {
            map.put("emailMsg", "邮箱未被注册！");
            return map;
        }
        // 重置密码
        password = CommunityUtil.md5(password + user.getSalt());
        userMapper.updatePassword(user.getId(), password);

        map.put("user", user);
        return map;
    }

    /**
     * @description: 修改密码
     * @date: 2022/5/25 11:11
     * @param: [userId, oldPassword, newPassword]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String, Object> updatePassword(int userId, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>();
        // 空值处理
        if(StringUtils.isBlank(oldPassword)) {
            map.put("oldPasswordMsg", "旧密码为空！");
            return map;
        }
        if (StringUtils.isBlank(newPassword)) {
            map.put("newPasswordMsg", "新密码为空！");
            return map;
        }
        // 验证邮箱
        User user = userMapper.selectById(userId);
        oldPassword = CommunityUtil.md5(oldPassword + user.getSalt());
        if(!user.getPassword().equals(oldPassword)) {
            map.put("oldPasswordMsg", "原密码输入有误！");
            return map;
        }
        // 修改密码
        newPassword = CommunityUtil.md5(newPassword + user.getSalt());
        userMapper.updatePassword(userId, newPassword);

        return map;
    }
    /**
     * @description: 根据名字查找用户
     * @date: 2022/5/28 22:22
     * @param: [name]
     * @return: com.nowcoder.community.entity.User
     **/
    public User findUserByName(String name) {
        return userMapper.selectByName(name);
    }

    /**
     * @description: 优先从缓存中取值
     * @date: 2022/6/1 20:47
     * @param: [userId]
     * @return: com.nowcoder.community.entity.User
     **/
    private User getCache(int userId) {
        String redisKey = RedisUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * @description: 缓存中取不到时初始化缓存数据
     * @date: 2022/6/1 20:48
     * @param: [userId]
     * @return: com.nowcoder.community.entity.User
     **/
    private User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String redisKey = RedisUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    /**
     * @description: 数据变更时清除缓存数据
     * @date: 2022/6/1 20:48
     * @param: [userId]
     * @return: void
     **/
    private void clearCache(int userId) {
        String redisKey = RedisUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }

    /**
     * @description: 获取授权信息
     * @date: 2022/6/13 12:39
     * @param: [userId]
     * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     **/
    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        User user = this.findUserById(userId);
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 1:
                        return AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_MODERATOR;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }
}
