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
@Table(name = "c_receive_message")
public class ReceiveMessage {
    /**
     * 流水ID
     */
    @Id
    @Column(name = "receiveMessageId")
    @KeySql(genId = UuidGenId.class)
    private String receiveMessageId;

    /**
     * 消息流水ID
     */
    @Column(name = "chatMessageId")
    private String chatMessageId;

    /**
     * 发送人流水ID
     */
    @Column(name = "sendUserId")
    private String sendUserId;

    /**
     * 接收人流水ID
     */
    @Column(name = "receiveUserId")
    private String receiveUserId;

    /**
     * 接收时间
     */
    @Column(name = "receiveDate")
    private Date receiveDate;

    /**
     * 查看时间
     */
    @Column(name = "readDate")
    private Date readDate;

    /**
     * 消息状态【0：删除；1：待阅；9：已阅】
     */
    @Column(name = "msgState")
    private String msgState;

    /**
     * 所属群组ID
     */
    @Column(name = "groupId")
    private String groupId;

    /**
     * 获取流水ID
     *
     * @return receiveMessageId - 流水ID
     */
    public String getReceiveMessageId() {
        return receiveMessageId;
    }

    /**
     * 设置流水ID
     *
     * @param receiveMessageId 流水ID
     */
    public void setReceiveMessageId(String receiveMessageId) {
        this.receiveMessageId = receiveMessageId;
    }

    /**
     * 获取消息流水ID
     *
     * @return chatMessageId - 消息流水ID
     */
    public String getChatMessageId() {
        return chatMessageId;
    }

    /**
     * 设置消息流水ID
     *
     * @param chatMessageId 消息流水ID
     */
    public void setChatMessageId(String chatMessageId) {
        this.chatMessageId = chatMessageId;
    }

    /**
     * 获取发送人流水ID
     *
     * @return sendUserId - 发送人流水ID
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * 设置发送人流水ID
     *
     * @param sendUserId 发送人流水ID
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * 获取接收人流水ID
     *
     * @return receiveUserId - 接收人流水ID
     */
    public String getReceiveUserId() {
        return receiveUserId;
    }

    /**
     * 设置接收人流水ID
     *
     * @param receiveUserId 接收人流水ID
     */
    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    /**
     * 获取接收时间
     *
     * @return receiveDate - 接收时间
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 设置接收时间
     *
     * @param receiveDate 接收时间
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * 获取查看时间
     *
     * @return readDate - 查看时间
     */
    public Date getReadDate() {
        return readDate;
    }

    /**
     * 设置查看时间
     *
     * @param readDate 查看时间
     */
    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    /**
     * 获取消息状态【0：删除；1：待阅；9：已阅】
     *
     * @return msgState - 消息状态【0：删除；1：待阅；9：已阅】
     */
    public String getMsgState() {
        return msgState;
    }

    /**
     * 设置消息状态【0：删除；1：待阅；9：已阅】
     *
     * @param msgState 消息状态【0：删除；1：待阅；9：已阅】
     */
    public void setMsgState(String msgState) {
        this.msgState = msgState;
    }

    /**
     * 获取所属群组ID
     *
     * @return groupId - 所属群组ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置所属群组ID
     *
     * @param groupId 所属群组ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}