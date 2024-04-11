package com.hycan.idn.hvip.pojo.vip;

import lombok.Data;

/**
 * H-VIP HTTP接口Header用户信息
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class LoginUserDTO {

    private Long userId;

    private String userName;

    public static LoginUserDTO of(Long userId, String userName) {
        LoginUserDTO user = new LoginUserDTO();
        user.setUserId(userId);
        user.setUserName(userName);
        return user;
    }
}
