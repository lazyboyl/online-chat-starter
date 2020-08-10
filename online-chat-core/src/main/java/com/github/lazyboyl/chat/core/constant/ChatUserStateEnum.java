package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzef
 * @since 2020-08-10
 * 类描述： 用户状态的枚举类
 */
public enum ChatUserStateEnum {

    /**
     * 注销
     */
    CANCEL("0"),
    /**
     * 冻结
     */
    FREEZE("1"),
    /**
     * 正常
     */
    NORMAL("9");

    private String userState;

    ChatUserStateEnum(String userState) {
        this.userState = userState;
    }

    public String getUserState() {
        return userState;
    }
}
