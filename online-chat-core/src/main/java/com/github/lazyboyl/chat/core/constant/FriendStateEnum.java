package com.github.lazyboyl.chat.core.constant;

/**
 * @author liznef
 * @since 2020-08-04
 * 类描述：好友状态的枚举类
 */
public enum FriendStateEnum {
    /**
     * 在线
     */
    ONLINE("9"),
    /**
     * 下线
     */
    OFFLINE("0");

    private String friendState;

    FriendStateEnum(String friendState) {
        this.friendState = friendState;
    }

    public String getFriendState() {
        return friendState;
    }
}
