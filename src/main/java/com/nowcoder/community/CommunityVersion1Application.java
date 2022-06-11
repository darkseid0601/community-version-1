package com.nowcoder.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CommunityVersion1Application {

    @PostConstruct
    public void init() {
        // 解决netty启动冲突的问题（主要体现在启动redis和elasticsearch）
        // redis -> netty; es -> netty 当redis启动后es会检查netty，发现已设置则会不启动，因此报错
        // 可以看Netty4Util.setAvailableProcessors(..)
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityVersion1Application.class, args);
    }

}
