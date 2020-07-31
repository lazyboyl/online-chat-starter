package com.github.lazyboyl.chat.core.entity;

import com.github.lazyboyl.chat.core.config.UuidGenId;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
@Table(name = "c_chat_message")
public class ChatMessage {
    /**
     * 流水ID
     */
    @Id
    @Column(name = "chatMessageId")
    @KeySql(genId = UuidGenId.class)
    private String chatMessageId;

    /**
     * 消息内容
     */
    @Column(name = "messageContent")
    private String messageContent;

    /**
     * 创建人流水ID
     */
    @Column(name = "crtUserId")
    private String crtUserId;

    /**
     * 创建人名称
     */
    @Column(name = "crtUserName")
    private String crtUserName;

    /**
     * 创建时间
     */
    @Column(name = "crtDate")
    private Date crtDate;

    /**
     * 消息类型【1：双人聊天；2：群组聊天】
     */
    @Column(name = "messageType")
    private String messageType;

    /**
     * 获取流水ID
     *
     * @return chatMessageId - 流水ID
     */
    public String getChatMessageId() {
        return chatMessageId;
    }

    /**
     * 设置流水ID
     *
     * @param chatMessageId 流水ID
     */
    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    /**
     * 获取消息内容
     *
     * @return messageContent - 消息内容
     */
    public String getMessageContent() {
        return messageContent;
    }

    /**
     * 设置消息内容
     *
     * @param messageContent 消息内容
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    /**
     * 获取创建人流水ID
     *
     * @return crtUserId - 创建人流水ID
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * 设置创建人流水ID
     *
     * @param crtUserId 创建人流水ID
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    /**
     * 获取创建人名称
     *
     * @return crtUserName - 创建人名称
     */
    public String getCrtUserName() {
        return crtUserName;
    }

    /**
     * 设置创建人名称
     *
     * @param crtUserName 创建人名称
     */
    public void setCrtUserName(String crtUserName) {
        this.crtUserName = crtUserName;
    }

    /**
     * 获取创建时间
     *
     * @return crtDate - 创建时间
     */
    public Date getCrtDate() {
        return crtDate;
    }

    /**
     * 设置创建时间
     *
     * @param crtDate 创建时间
     */
    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}