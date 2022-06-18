package com.nowcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.config
 * @CreateTime: 2022-06-17  21:44
 * @Description: 线程池配置
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ThreadPoolConfig {
}
