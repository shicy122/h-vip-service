package com.hycan.idn.hvip.kafka;

import com.hycan.idn.common.core.util.JsonUtil;
import com.hycan.idn.hvip.constants.KafkaConstants;
import com.hycan.idn.hvip.service.IOrderService;
import com.hycan.idn.station.pojo.SyncOrderStatusDTO;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


/**
 * 同步工单状态
 *
 * @author shichongying
 * @datetime 2023-10-21 11:26
 */
@Component
@Slf4j
public class KafkaConsumeListener implements BatchAcknowledgingMessageListener<String, String> {

    @Resource
    private IOrderService orderService;

    @Override
    @KafkaListener(topics = KafkaConstants.SYNC_ORDER_ITEM_STATUS)
    public void onMessage(@NonNull List<ConsumerRecord<String, String>> records, @NonNull Acknowledgment ack) {
        try {
            for (final ConsumerRecord<String, String> record : records) {
                SyncOrderStatusDTO syncDto = JsonUtil.readValue(record.value(), SyncOrderStatusDTO.class);
                if (Objects.isNull(syncDto)) {
                    continue;
                }

            }
        } catch (Exception e) {
            log.error("处理Kafka消息异常={}", ExceptionUtil.getExceptionCause(e));
        } finally {
            ack.acknowledge();
        }
    }
}