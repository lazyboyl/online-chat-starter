package com.github.lazyboyl.chat.core.vo;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 查询好友的VO
 */
public class QueryFriendVo {

    /**
     * 用户流水ID
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户头像地址
     */
    private String avatar;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
