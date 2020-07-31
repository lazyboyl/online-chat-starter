package com.github.lazyboyl.chat.core.entity;

import com.github.lazyboyl.chat.core.config.UuidGenId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述： 好友实体类
 */
@Table(name = "c_friend_list")
public class FriendList {
    /**
     * 好友列表流水ID
     */
    @Id
    @Column(name = "friendListId")
    @KeySql(genId = UuidGenId.class)
    private String friendListId;

    /**
     * 好友ID
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 所属用户ID
     */
    @Column(name = "belowUserId")
    private String belowUserId;

    /**
     * 好友状态【0：已下线；9：已上线】
     */
    @Column(name = "friendState")
    private String friendState;

    /**
     * 用户昵称
     */
    @Column(name = "nickName")
    private String nickName;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 分组所属ID
     */
    @Column(name = "friendGroupId")
    private String friendGroupId;


    /**
     * 获取好友列表流水ID
     *
     * @return friendListId - 好友列表流水ID
     */
    public String getFriendListId() {
        return friendListId;
    }

    /**
     * 设置好友列表流水ID
     *
     * @param friendListId 好友列表流水ID
     */
    public void setFriendListId(String friendListId) {
        this.friendListId = friendListId;
    }

    /**
     * 获取好友ID
     *
     * @return userId - 好友ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置好友ID
     *
     * @param userId 好友ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取所属用户ID
     *
     * @return belowUserId - 所属用户ID
     */
    public String getBelowUserId() {
        return belowUserId;
    }

    /**
     * 设置所属用户ID
     *
     * @param belowUserId 所属用户ID
     */
    public void setBelowUserId(String belowUserId) {
        this.belowUserId = belowUserId;
    }

    /**
     * 获取好友状态【0：已下线；9：已上线】
     *
     * @return friendState - 好友状态【0：已下线；9：已上线】
     */
    public String getFriendState() {
        return friendState;
    }

    /**
     * 设置好友状态【0：已下线；9：已上线】
     *
     * @param friendState 好友状态【0：已下线；9：已上线】
     */
    public void setFriendState(String friendState) {
        this.friendState = friendState;
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
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取分组所属ID
     *
     * @return friendGroupId - 分组所属ID
     */
    public String getFriendGroupId() {
        return friendGroupId;
    }

    /**
     * 设置分组所属ID
     *
     * @param friendGroupId 分组所属ID
     */
    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }


}