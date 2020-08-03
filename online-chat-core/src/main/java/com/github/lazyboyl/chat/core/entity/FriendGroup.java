package com.github.lazyboyl.chat.core.entity;

import com.github.lazyboyl.chat.core.config.UuidGenId;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述： 好友分组的实体类
 */
@Table(name = "c_friend_group")
public class FriendGroup {
    /**
     * 分组流水ID
     */
    @Id
    @Column(name = "friendGroupId")
    @KeySql(genId = UuidGenId.class)
    private String friendGroupId;

    /**
     * 分组归属用户ID
     */
    @Column(name = "crtUserId")
    private String crtUserId;

    /**
     * 分组名称
     */
    @Column(name = "friendGroupName")
    private String friendGroupName;

    /**
     * 分组排序
     */
    @Column(name = "friendGroupOrder")
    private Integer friendGroupOrder;

    /**
     * 是否允许删除【0：允许删除；9：不允许删除】
     */
    @Column(name = "allowDeletion")
    private String allowDeletion;


    /**
     * 好友分组中的好友列表的数据
     */
    @Transient
    private List<Friend> friendLists;

    public List<Friend> getFriendLists() {
        return friendLists;
    }

    public void setFriendLists(List<Friend> friendLists) {
        this.friendLists = friendLists;
    }

    /**
     * 获取分组流水ID
     *
     * @return friendGroupId - 分组流水ID
     */
    public String getFriendGroupId() {
        return friendGroupId;
    }

    /**
     * 设置分组流水ID
     *
     * @param friendGroupId 分组流水ID
     */
    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    /**
     * 获取分组归属用户ID
     *
     * @return crtUserId - 分组归属用户ID
     */
    public String getCrtUserId() {
        return crtUserId;
    }

    /**
     * 设置分组归属用户ID
     *
     * @param crtUserId 分组归属用户ID
     */
    public void setCrtUserId(String crtUserId) {
        this.crtUserId = crtUserId;
    }

    /**
     * 获取分组名称
     *
     * @return friendGroupName - 分组名称
     */
    public String getFriendGroupName() {
        return friendGroupName;
    }

    /**
     * 设置分组名称
     *
     * @param friendGroupName 分组名称
     */
    public void setFriendGroupName(String friendGroupName) {
        this.friendGroupName = friendGroupName;
    }

    /**
     * 获取分组排序
     *
     * @return friendGroupOrder - 分组排序
     */
    public Integer getFriendGroupOrder() {
        return friendGroupOrder;
    }

    /**
     * 设置分组排序
     *
     * @param friendGroupOrder 分组排序
     */
    public void setFriendGroupOrder(Integer friendGroupOrder) {
        this.friendGroupOrder = friendGroupOrder;
    }

    /**
     * 获取是否允许删除【0：允许删除；9：不允许删除】
     *
     * @return allowDeletion - 是否允许删除【0：允许删除；9：不允许删除】
     */
    public String getAllowDeletion() {
        return allowDeletion;
    }

    /**
     * 设置是否允许删除【0：允许删除；9：不允许删除】
     *
     * @param allowDeletion 是否允许删除【0：允许删除；9：不允许删除】
     */
    public void setAllowDeletion(String allowDeletion) {
        this.allowDeletion = allowDeletion;
    }

}