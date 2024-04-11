package com.hycan.idn.hvip.pojo.avp.http.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AVP登录用户请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserReqVO implements Serializable {

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    public static LoginUserReqVO of(String username, String password) {
        LoginUserReqVO req = new LoginUserReqVO();
        req.setUsername(username);
        req.setPassword(password);
        return req;
    }
}