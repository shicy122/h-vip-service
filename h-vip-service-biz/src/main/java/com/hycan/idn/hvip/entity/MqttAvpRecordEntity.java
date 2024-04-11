package com.hycan.idn.hvip.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * AVP MQTT消息记录Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:23
 */
@Data
@Entity
@Table(name = "mqtt_avp_record")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class MqttAvpRecordEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("MQTT主题")
    @Column(name = "topic", nullable = false, length = 50)
    private String topic;

    @Comment("消息体")
    @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
    private String payload;

    @Comment("记录时间")
    @Column(name = "record_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime recordTime;

    public static MqttAvpRecordEntity of(String topic, String payload) {
        MqttAvpRecordEntity entity = new MqttAvpRecordEntity();
        entity.setTopic(topic);
        entity.setPayload(payload);
        return entity;
    }
}
