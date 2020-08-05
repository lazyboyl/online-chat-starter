package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述：
 */
public enum MsgTypeEnum {
    /**
     * 用户聊天消息
     */
    USERMSG("1"),
    /**
     * 群组聊天消息
     */
    GROUPMSG("2"),
    /**
     * 好友申请
     */
    FRIENDAPPLY("3"),
    /**
     * 加群申请
     */
    GROUPAPPLY("4"),
    /**
     * 邀请入群
     */
    APPLYGROUP("5"),
    /**
     * 移除好友
     */
    REMOVEFRIEND("6"),
    /**
     * 移出群
     */
    REMOVEGROUP("7"),
    /**
     * 登录
     */
    LOGIN("8"),
    /**
     * 好友审核的通知
     */
    VERIFYFRIEND("9");

    private String type;

    MsgTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
