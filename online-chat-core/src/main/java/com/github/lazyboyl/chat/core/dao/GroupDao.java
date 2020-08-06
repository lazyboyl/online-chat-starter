package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.Group;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface GroupDao extends Mapper<Group> {

    /**
     * 功能描述： 实现群组的迁移
     *
     * @param transGroupId 迁移目标的群组ID
     * @param groupId      待迁移的群组ID
     * @param crtUserId    创建人流水ID
     * @return
     */
    int transferGroup(@Param("transGroupId") String transGroupId, @Param("groupId") String groupId, @Param("crtUserId") String crtUserId);

    /**
     * 功能描述： 更新群组信息
     *
     * @param groupId   群流水ID
     * @param groupName 群名称
     * @param groupImg  群图标
     * @param crtUserId 创建人流水ID
     * @return
     */
    int updateGroup(@Param("groupId") String groupId, @Param("groupName") String groupName, @Param("groupImg") String groupImg, @Param("crtUserId") String crtUserId);

    /**
     * 功能描述： 验证当前的群组名称是否被使用了
     *
     * @param groupName 群组名称
     * @param crtUserId 所属人流水ID
     * @return 返回验证结果
     */
    int checkGroupName(@Param("groupName") String groupName, @Param("crtUserId") String crtUserId);

}