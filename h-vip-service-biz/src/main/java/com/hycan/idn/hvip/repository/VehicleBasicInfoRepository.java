package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.VehicleBasicInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * 车辆基础信息 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
public interface VehicleBasicInfoRepository extends JpaRepository<VehicleBasicInfoEntity, String> {

    /**
     * 查询单条车辆基础信息
     *
     * @param vin VIN码
     * @return 车辆基础信息Entity
     */
    VehicleBasicInfoEntity findByVin(String vin);

    /**
     * 根据车牌号查询VIN码
     *
     * @param plateNo 车牌号
     * @return VIN码
     */
    VehicleBasicInfoEntity findByPlateNo(String plateNo);

    /**
     * 更新车控秘钥
     *
     * @param vin    VIN码
     * @param secret 车控秘钥
     */
    @Transactional
    @Modifying
    @Query("UPDATE VehicleBasicInfoEntity v SET v.ccmSecret = :secret WHERE v.vin = :vin")
    void updateVehicleSecret(@Param("vin") String vin, @Param("secret") String secret);
}
