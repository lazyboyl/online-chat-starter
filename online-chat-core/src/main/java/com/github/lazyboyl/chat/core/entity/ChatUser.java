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
@Table(name = "c_chat_user")
public class ChatUser {
    /**
     * 用户流水ID
     */
    @Id
    @Column(name = "userId")
    @KeySql(genId = UuidGenId.class)
    private String userId;

    /**
     * 用户昵称
     */
    @Column(name = "nickName")
    private String nickName;

    /**
     * 用户账号
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 用户状态【0：注销；1：冻结；9：正常】
     */
    @Column(name = "userState")
    private String userState;

    /**
     * 创建时间
     */
    @Column(name = "crtDate")
    private Date crtDate;

    /**
     * 同步系统流水ID
     */
    @Column(name = "syncUserId")
    private String syncUserId;

    /**
     * 用户编号
     */
    @Column(name = "userNo")
    private String userNo;

    /**
     * 获取用户流水ID
     *
     * @return userId - 用户流水ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户流水ID
     *
     * @param userId 用户流水ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户昵称
     *
     * @return nickName - 用户昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置用户昵称
     *
     * @param nickName 用户昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取用户账号
     *
     * @return userName - 用户账号
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户账号
     *
     * @param userName 用户账号
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户状态【0：注销；1：冻结；9：正常】
     *
     * @return userState - 用户状态【0：注销；1：冻结；9：正常】
     */
    public String getUserState() {
        return userState;
    }

    /**
     * 设置用户状态【0：注销；1：冻结；9：正常】
     *
     * @param userState 用户状态【0：注销；1：冻结；9：正常】
     */
    public void setUserState(String userState) {
        this.userState = userState;
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

    /**
     * 获取同步系统流水ID
     *
     * @return syncUserId - 同步系统流水ID
     */
    public String getSyncUserId() {
        return syncUserId;
    }

    /**
     * 设置同步系统流水ID
     *
     * @param syncUserId 同步系统流水ID
     */
    public void setSyncUserId(String syncUserId) {
        this.syncUserId = syncUserId;
    }

    /**
     * 获取用户编号
     *
     * @return userNo - 用户编号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 设置用户编号
     *
     * @param userNo 用户编号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}