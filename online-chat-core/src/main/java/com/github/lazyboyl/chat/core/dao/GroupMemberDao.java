package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.GroupMember;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface GroupMemberDao extends Mapper<GroupMember> {

    /**
     * 功能描述： 验证当前用户是否在当前群组中
     *
     * @param groupId 当前群组ID
     * @param userId  群组中的用户ID
     * @return
     */
    int checkUserIsInGroup(String groupId, String userId);

}