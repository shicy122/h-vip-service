package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.MqttMpRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * H-VIP小程序MQTT消息记录 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
@Repository
public interface MqttMpRecordRepository extends JpaRepository<MqttMpRecordEntity, String> {
}
