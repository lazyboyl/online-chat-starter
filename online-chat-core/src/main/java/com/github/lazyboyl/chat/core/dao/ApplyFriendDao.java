package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.ApplyFriend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface ApplyFriendDao extends Mapper<ApplyFriend> {


    /**
     * 功能描述： 获取好友申请列表
     * @param targetUserId 当前接收消息的用户的流水ID
     * @return 返回查询结果
     */
    List<ApplyFriend> getApplyFriendList(@Param("targetUserId") String targetUserId);

    /**
     * 功能描述： 审核好友
     *
     * @param applyFriendId 好友申请流水表ID
     * @param applyUserId   申请人ID
     * @param applyState    审核状态 【0：拒绝；9：通过】
     * @param verifyDate    审核时间
     * @return
     */
    int verifyFriend(@Param("applyFriendId") String applyFriendId,
                     @Param("applyUserId") String applyUserId,
                     @Param("applyState") String applyState,
                     @Param("verifyDate") Date verifyDate);

    /**
     * 功能描述：根据申请人流水ID和接收人流水ID来获取申请好友的信息
     *
     * @param applyUserId  申请人流水ID
     * @param targetUserId 接收人流水ID
     * @return 返回查询结果
     */
    ApplyFriend queryApplyFriendByApplyUserIdAndTargetUserId(@Param("applyUserId") String applyUserId, @Param("targetUserId") String targetUserId);

}