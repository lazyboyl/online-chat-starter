package com.github.lazyboyl.chat.core.entity;

import com.github.lazyboyl.chat.core.config.UuidGenId;
import tk.mybatis.mapper.annotation.KeySql;

import java.util.Date;
import javax.persistence.*;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述： 群组实体类
 */
@Table(name = "c_group")
public class Group {
    /**
     * 群组流水ID
     */
    @Id
    @Column(name = "groupId")
    @KeySql(genId = UuidGenId.class)
    private String groupId;

    /**
     * 群组名称
     */
    @Column(name = "groupName")
    private String groupName;

    /**
     * 群组图标地址
     */
    @Column(name = "groupImg")
    private String groupImg;

    /**
     * 创建时间
     */
    @Column(name = "crtDate")
    private Date crtDate;

    /**
     * 创建人流水ID
     */
    @Column(name = "crtUserId")
    private String crtUserId;

    /**
     * 获取群组流水ID
     *
     * @return groupId - 群组流水ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置群组流水ID
     *
     * @param groupId 群组流水ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取群组名称
     *
     * @return groupName - 群组名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置群组名称
     *
     * @param groupName 群组名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取群组图标地址
     *
     * @return groupImg - 群组图标地址
     */
    public String getGroupImg() {
        return groupImg;
    }

    /**
     * 设置群组图标地址
     *
     * @param groupImg 群组图标地址
     */
    public void setGroupImg(String groupImg) {
        this.groupImg = groupImg;
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
}