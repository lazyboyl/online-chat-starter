package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzef
 * @since 2020-08-04
 * 类描述：系统常量的枚举类
 */
public enum SystemEnum {

    /**
     * 成功的标志
     */
    SUCCESS(200),
    /**
     * 失败的标志
     */
    FAIL(400),
    /**
     * token过期的标志
     */
    AUTH_TOKEN_EXPIRE(409),
    /**
     * 无权限的返回
     */
    AUTH_FAIL(403),
    /**
     * 未登录的返回
     */
    NOT_LOGIN(401);

    private int key;

    SystemEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

}
