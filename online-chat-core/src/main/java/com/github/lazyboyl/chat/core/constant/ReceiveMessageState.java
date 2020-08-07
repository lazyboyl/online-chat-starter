package com.github.lazyboyl.chat.core.constant;

/**
 * @author linzef
 * @since 2020-08-07
 * 类描述：消息接收的状态
 */
public enum ReceiveMessageState {

    /**
     * 删除
     */
    DELETE("0"),
    /**
     * 待阅
     */
    WAIT_READ("1"),
    /**
     * 已阅
     */
    READ("9");

    private String msgState;

    ReceiveMessageState(String msgState) {
        this.msgState = msgState;
    }

    public String getMsgState() {
        return msgState;
    }
}
