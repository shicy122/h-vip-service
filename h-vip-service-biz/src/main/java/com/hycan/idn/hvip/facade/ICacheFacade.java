package com.hycan.idn.hvip.facade;

/**
 * 本地缓存包装类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:42
 */
public interface ICacheFacade {

    /**
     * 从缓存中，根据AVP场站编号，查询H-VIP场站编号
     *
     * @param avpStationCode AVP场站编号
     * @return H-VIP场站编号
     */
    String getStationCodeByAvpStationCodeFromCache(String avpStationCode);

    /**
     * 从缓存中，根据工单编号，查询AVP场站编号
     *
     * @param orderCode 工单编号
     * @return AVP场站编号
     */
    String getAvpStationCodeByOrderCodeFromCache(String orderCode);

    /**
     * 从缓存中，根据工单编号，查询VIN码
     *
     * @param orderCode 工单编号
     * @return VIN码
     */
    String getVinByOrderCodeFromCache(String orderCode);

    /**
     * 从缓存中，根据VIN码，查询车牌号
     *
     * @param vin VIN码
     * @return 车牌号
     */
    String getPlateNoByVinFromCache(String vin);

    /**
     * 从缓存中，根据车牌号，查询VIN码
     *
     * @param plateNo 车牌号
     * @return VIN码
     */
    String getVinByPlateNoFromCache(String plateNo);

    /**
     * 从缓存中，根据VIN码，查询车辆当前所在的H-VIP场站编号
     *
     * @param vin VIN码
     * @return H-VIP场站编号
     */
    String getCurrentStationCodeByVinFromCache(String vin);
}
