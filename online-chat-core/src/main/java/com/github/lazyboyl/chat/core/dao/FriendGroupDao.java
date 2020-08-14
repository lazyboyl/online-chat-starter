package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.FriendGroup;
import com.github.lazyboyl.chat.core.vo.MyFriendListVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface FriendGroupDao extends Mapper<FriendGroup> {

    /**
     * 功能描述： 获取当前的我的好友分组的排序的最大的值
     *
     * @param crtUserId 创建人流水ID
     * @return 返回查询结果
     */
    int getMaxFriendGroupOrder(@Param("crtUserId") String crtUserId);

    /**
     * 功能描述： 获取我的好友分组列表数据
     *
     * @param crtUserId 创建人流水ID
     * @return 返回查询结果
     */
    List<FriendGroup> getFriendGroupList(@Param("crtUserId") String crtUserId);

    /**
     * 功能描述： 获取我的好友列表
     *
     * @param crtUserId 当前登录的用户的流水ID
     * @return 返回我的好友列表的数据
     */
    List<MyFriendListVo> myFriendList(@Param("crtUserId") String crtUserId);

}