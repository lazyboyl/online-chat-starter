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
@Table(name = "c_apply_friend")
public class ApplyFriend {
    /**
     * 流水ID
     */
    @Id
    @Column(name = "applyFriendId")
    @KeySql(genId = UuidGenId.class)
    private String applyFriendId;

    /**
     * 申请人流水ID
     */
    @Column(name = "applyUserId")
    private String applyUserId;

    /**
     * 申请人名称
     */
    @Column(name = "applyNickName")
    private String applyNickName;

    /**
     * 接收人ID
     */
    @Column(name = "targetUserId")
    private String targetUserId;

    /**
     * 申请信息
     */
    @Column(name = "note")
    private String note;

    /**
     * 申请时间
     */
    @Column(name = "applyDate")
    private Date applyDate;

    /**
     * 申请状态【0：拒绝；1：待通过；2：过期；9：通过】
     */
    @Column(name = "applyState")
    private String applyState;

    /**
     * 审核时间
     */
    @Column(name = "verifyDate")
    private Date verifyDate;

    /**
     * 申请类型【1：好友申请；】
     */
    @Column(name = "applyType")
    private String applyType;

    /**
     * 分组流水ID
     */
    @Column(name = "friendGroupId")
    private String friendGroupId;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    public String getFriendGroupId() {
        return friendGroupId;
    }

    public void setFriendGroupId(String friendGroupId) {
        this.friendGroupId = friendGroupId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取流水ID
     *
     * @return applyFriendId - 流水ID
     */
    public String getApplyFriendId() {
        return applyFriendId;
    }

    /**
     * 设置流水ID
     *
     * @param applyFriendId 流水ID
     */
    public void setApplyFriendId(String applyFriendId) {
        this.applyFriendId = applyFriendId;
    }

    /**
     * 获取申请人流水ID
     *
     * @return applyUserId - 申请人流水ID
     */
    public String getApplyUserId() {
        return applyUserId;
    }

    /**
     * 设置申请人流水ID
     *
     * @param applyUserId 申请人流水ID
     */
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    /**
     * 获取申请人名称
     *
     * @return applyNickName - 申请人名称
     */
    public String getApplyNickName() {
        return applyNickName;
    }

    /**
     * 设置申请人名称
     *
     * @param applyNickName 申请人名称
     */
    public void setApplyNickName(String applyNickName) {
        this.applyNickName = applyNickName;
    }

    /**
     * 获取接收人ID
     *
     * @return targetUserId - 接收人ID
     */
    public String getTargetUserId() {
        return targetUserId;
    }

    /**
     * 设置接收人ID
     *
     * @param targetUserId 接收人ID
     */
    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    /**
     * 获取申请信息
     *
     * @return note - 申请信息
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置申请信息
     *
     * @param note 申请信息
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取申请时间
     *
     * @return applyDate - 申请时间
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 设置申请时间
     *
     * @param applyDate 申请时间
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * 获取申请状态【0：拒绝；1：待通过；2：过期；9：通过】
     *
     * @return applyState - 申请状态【0：拒绝；1：待通过；2：过期；9：通过】
     */
    public String getApplyState() {
        return applyState;
    }

    /**
     * 设置申请状态【0：拒绝；1：待通过；2：过期；9：通过】
     *
     * @param applyState 申请状态【0：拒绝；1：待通过；2：过期；9：通过】
     */
    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    /**
     * 获取审核时间
     *
     * @return verifyDate - 审核时间
     */
    public Date getVerifyDate() {
        return verifyDate;
    }

    /**
     * 设置审核时间
     *
     * @param verifyDate 审核时间
     */
    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
}