package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community
 * @CreateTime: 2022-05-20  20:05
 * @Description:
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityVersion1Application.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testTextMail() {
        mailClient.sendMail("1378776476@qq.com", "Test", "123456");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "sunday");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("1378776476@qq.com", "HTML", content);
    }
}
