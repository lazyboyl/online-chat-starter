package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.ApplyFriend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface ApplyFriendDao extends Mapper<ApplyFriend> {

    /**
     * 功能描述：根据申请人流水ID和接收人流水ID来获取申请好友的信息
     * @param applyUserId 申请人流水ID
     * @param targetUserId 接收人流水ID
     * @return 返回查询结果
     */
    ApplyFriend queryApplyFriendByApplyUserIdAndTargetUserId(@Param("applyUserId") String applyUserId,@Param("targetUserId") String targetUserId);

}