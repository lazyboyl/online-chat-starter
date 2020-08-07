package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.GroupMember;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface GroupMemberDao extends Mapper<GroupMember> {

    /**
     * 功能描述： 判断当前用户是否在这个群组中
     *
     * @param groupId 群组流水ID
     * @param userId  用户流水ID
     * @return 返回查询结果
     */
    int isUserInGroup(@Param("groupId") String groupId, @Param("userId") String userId);

    /**
     * 功能描述： 查询群组底下的成员
     *
     * @param groupId 群组ID
     * @param userId  不需要查询的流水ID
     * @return 返回查询结果
     */
    List<GroupMember> getGroupMember(@Param("groupId") String groupId, @Param("userId") String userId);

    /**
     * 功能描述： 将用户从群组中移除
     *
     * @param groupId     群组ID
     * @param userId      待移除用户流水ID
     * @param belowUserId 当前群组所属用户ID
     * @return
     */
    int removeUserGroup(@Param("groupId") String groupId, @Param("userId") String userId, @Param("belowUserId") String belowUserId);

    /**
     * 功能描述： 验证当前用户是否在当前群组中
     *
     * @param groupId 当前群组ID
     * @param userId  群组中的用户ID
     * @return
     */
    int checkUserIsInGroup(@Param("groupId") String groupId, @Param("userId") String userId);

}