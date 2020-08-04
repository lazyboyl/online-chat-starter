package com.github.lazyboyl.chat.core.vo;

import com.github.lazyboyl.chat.core.entity.Friend;

import java.util.List;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 我的好友列表的vo
 */
public class MyFriendListVo {

    /**
     * 分组流水ID
     */
    private String friendGroupId;

    /**
     * 分组名称
     */
    private String friendGroupName;

    /**
     * 分组排序
     */
    private Integer friendGroupOrder;

    /**
     * 是否允许删除【0：允许删除；9：不允许删除】
     */
    private String allowDeletion;

    /**
     * 好友分组中的好友列表的数据
     */
    private List<Friend> friendLists;


    public String getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public String getFriendGroupName() {
        return friendGroupName;
    }

    public void setFriendGroupName(String friendGroupName) {
        this.friendGroupName = friendGroupName;
    }

    public Integer getFriendGroupOrder() {
        return friendGroupOrder;
    }

    public void setFriendGroupOrder(Integer friendGroupOrder) {
        this.friendGroupOrder = friendGroupOrder;
    }

    public String getAllowDeletion() {
        return allowDeletion;
    }

    public void setAllowDeletion(String allowDeletion) {
        this.allowDeletion = allowDeletion;
    }

    public List<Friend> getFriendLists() {
        return friendLists;
    }

    public void setFriendLists(List<Friend> friendLists) {
        this.friendLists = friendLists;
    }
}
