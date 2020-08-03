package com.github.lazyboyl.chat.core.entity;

import com.github.lazyboyl.chat.core.config.UuidGenId;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述： 群组所在好友的实体类
 */
@Table(name = "c_group_member")
public class GroupMember {
    /**
     * 流水ID
     */
    @Id
    @Column(name = "groupMemberId")
    @KeySql(genId = UuidGenId.class)
    private String groupMemberId;

    /**
     * 群流水ID
     */
    @Column(name = "groupId")
    private String groupId;

    /**
     * 成员流水ID
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 加入时间
     */
    @Column(name = "joinDate")
    private Date joinDate;

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
     * 用户头像地址
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 获取流水ID
     *
     * @return groupMemberId - 流水ID
     */
    public String getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * 设置流水ID
     *
     * @param groupMemberId 流水ID
     */
    public void setGroupMemberId(String groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    /**
     * 获取群流水ID
     *
     * @return groupId - 群流水ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置群流水ID
     *
     * @param groupId 群流水ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取成员流水ID
     *
     * @return userId - 成员流水ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置成员流水ID
     *
     * @param userId 成员流水ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取加入时间
     *
     * @return joinDate - 加入时间
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * 设置加入时间
     *
     * @param joinDate 加入时间
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}