package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.Friend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface FriendDao extends Mapper<Friend> {

    /**
     * 功能描述： 好友上下线的时候更新好友状态
     *
     * @param userId      当前通道的用户ID
     * @param friendState 当前需要更新的状态
     * @return 返回更新结果
     */
    int friendOnOff(@Param("userId") String userId, @Param("friendState") String friendState);

    /**
     * 功能描述： 判断当前的好友是否已经是你的好友
     *
     * @param userId      当前待添加的好友的流水ID
     * @param belowUserId 当前登录用户的流水ID
     * @return 返回结果
     */
    int isYourFriend(@Param("userId") String userId, @Param("belowUserId") String belowUserId);

}