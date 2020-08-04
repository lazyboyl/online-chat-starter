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
     * 功能描述： 获取我的好友列表
     * @param crtUserId 当前登录的用户的流水ID
     * @return 返回我的好友列表的数据
     */
    List<MyFriendListVo> myFriendList(@Param("crtUserId") String crtUserId);

}