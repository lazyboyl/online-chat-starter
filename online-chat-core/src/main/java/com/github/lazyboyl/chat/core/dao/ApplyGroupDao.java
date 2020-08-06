package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.ApplyGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author linzef
 * @since 2020-08-06
 * 类描述：
 */
public interface ApplyGroupDao extends Mapper<ApplyGroup> {

    /**
     * 功能描述： 更新入群的申请的状态
     * @param applyState 审核状态
     * @param verifyDate 审核时间
     * @param applyUserId 申请人流水ID
     * @param targetUserId 接收人流水ID
     * @return
     */
    int updateApplyGroupApplyState(String applyState, Date verifyDate, String applyUserId, String targetUserId);

    /**
     * 功能描述： 判断当前是否已经存在申请了
     *
     * @param applyUserId  申请人流水ID
     * @param targetUserId 接收人流水ID
     * @param groupId      群组ID
     * @param applyType    申请类型
     * @return 返回申请查询结果
     */
    ApplyGroup getApplyGroupInfo(@Param("applyUserId") String applyUserId, @Param("targetUserId") String targetUserId, @Param("groupId") String groupId, @Param("applyType") String applyType);

}