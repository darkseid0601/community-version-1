package com.nowcoder.community.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.config
 * @CreateTime: 2022-06-11  11:08
 * @Description:
 */
@Configuration
public class ESConfig {
    @Value("${elasticSearch.url}")
    private String esUrl;

    //localhost:9200 写在配置文件中,直接用 <- elasticSearch.url
    @Bean
    RestHighLevelClient client() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esUrl)//elasticsearch地址
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
