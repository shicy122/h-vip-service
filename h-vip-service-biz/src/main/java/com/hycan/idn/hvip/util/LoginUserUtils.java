package com.hycan.idn.hvip.util;

import com.hycan.idn.hvip.pojo.vip.LoginUserDTO;

import java.util.Objects;

/**
 * 存储/获取当前线程的用户信息工具类
 */
public abstract class LoginUserUtils {

	//线程变量，存放user实体类信息，即使是静态的与其他线程也是隔离的
    private static final ThreadLocal<LoginUserDTO> USER_THREAD_LOCAL = new ThreadLocal<LoginUserDTO>();

	//从当前线程变量中获取用户信息
    public static LoginUserDTO getLoginUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     *
     * @return
     */
    public static Long getLoginUserId() {
        LoginUserDTO user = getLoginUser();
        if (Objects.nonNull(user) && Objects.nonNull(user.getUserId())) {
            return user.getUserId();
        }

        return null;
    }

 	//为当前的线程变量赋值上用户信息
    public static void setLoginUser(LoginUserDTO user) {
        USER_THREAD_LOCAL.set(user);
    }

	//清除线程变量
    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
