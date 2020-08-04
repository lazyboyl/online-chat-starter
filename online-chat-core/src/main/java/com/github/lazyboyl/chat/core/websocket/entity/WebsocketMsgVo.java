package com.github.lazyboyl.chat.core.websocket.entity;

import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 通讯的vo实体
 */
public class WebsocketMsgVo {

    /**
     * 1：用户聊天消息；2：群组聊天消息；3：好友申请；4：入群申请；5：邀请入群；6：移除好友；7：移除群组；8：登录
     */
    private String msgType;

    /**
     * 返回的数据信息
     */
    private Map<String, Object> data;

    /**
     * 默认构造方法
     */
    public WebsocketMsgVo() {
        super();
    }

    /**
     * 构造方法
     *
     * @param msgType 消息类型
     * @param data    数据
     */
    public WebsocketMsgVo(String msgType, Map<String, Object> data) {
        this.msgType = msgType;
        this.data = data;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
