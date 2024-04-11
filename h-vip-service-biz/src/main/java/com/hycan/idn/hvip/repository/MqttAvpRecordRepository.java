package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.MqttAvpRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AVP MQTT消息记录 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
@Repository
public interface MqttAvpRecordRepository extends JpaRepository<MqttAvpRecordEntity, String> {
}
