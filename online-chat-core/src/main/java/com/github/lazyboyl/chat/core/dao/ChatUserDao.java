package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.ChatUser;
import com.github.lazyboyl.chat.core.vo.QueryFriendVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述： 用户的dao
 */
public interface ChatUserDao extends Mapper<ChatUser> {

    /**
     * 功能描述： 查询好友
     *
     * @param userNo   用户编号
     * @param nickName 用户昵称
     * @return 查询好友的数据
     */
    List<QueryFriendVo> queryFriend(@Param("userNo") String userNo, @Param("nickName") String nickName);

}