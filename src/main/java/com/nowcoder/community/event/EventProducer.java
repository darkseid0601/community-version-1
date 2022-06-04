package com.nowcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: community-version-1
 * @BelongsPackage: com.nowcoder.community.event
 * @CreateTime: 2022-06-04  16:25
 * @Description: 事件生产者
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * @description: 处理事件
     * @date: 2022/6/4 16:33
     * @param: [event]
     * @return: void
     **/
    public void fireEvent(Event event) {
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
