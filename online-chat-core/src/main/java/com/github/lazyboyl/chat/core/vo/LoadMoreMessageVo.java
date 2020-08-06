package com.github.lazyboyl.chat.core.vo;

/**
 * @author linzf
 * @since 2020/8/6
 * 类描述：查看更多消息的VO
 */
public class LoadMoreMessageVo {

    /**
     * 聊天流水ID
     */
    private String chatMessageId;

    /**
     * 接收人流水ID
     */
    private String receiveUserId;

    /**
     * 发送人流水ID
     */
    private String sendUserId;

    /**
     * 接收时间
     */
    private String talkDate;

    /**
     * 聊天内容
     */
    private String talkContent;

    public String getChatMessageId() {
        return chatMessageId;
    }

    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(String talkDate) {
        this.talkDate = talkDate;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

}
