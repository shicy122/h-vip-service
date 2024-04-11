package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.VehicleParkRecordEntity;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleParkRspVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 车辆泊车记录 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
public interface VehicleParkRecordRepository extends JpaRepository<VehicleParkRecordEntity, String> {

    @Query(value = "SELECT vpr.vin AS vin, vpr.station_code AS stationCode, vpr.plate_no AS plateNo, " +
            "vpr.park_no AS parkNo, obi.order_code AS orderCode, vpr.record_time AS recordTime " +
            "FROM station_park_info spi " +
            "LEFT JOIN vehicle_park_record vpr ON spi.vin = vpr.vin " +
            "LEFT JOIN order_basic_info obi ON spi.vin = obi.vin " +
            "WHERE spi.vin = :vin AND obi.start_time <= CURRENT_TIMESTAMP AND obi.end_time >= CURRENT_TIMESTAMP " +
            "ORDER BY vpr.record_time DESC LIMIT 1", nativeQuery = true)
    VehicleParkRspVO selectVehicleCurrentPark(@Param("vin") String vin);
}
