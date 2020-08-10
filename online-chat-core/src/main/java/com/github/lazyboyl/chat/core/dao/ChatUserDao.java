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
     * 功能描述： 根据外部流水ID来获取用户
     *
     * @param syncUserId 外部流水ID
     * @return 返回获取结果
     */
    ChatUser queryChatUserBySyncUserId(@Param("syncUserId") String syncUserId);

    /**
     * 功能描述： 根据外部流水ID来更新用户状态
     *
     * @param syncUserId 外部流水ID
     * @param userState  用户状态
     * @return 返回删除结果
     */
    int updateChatUserStateBySyncUserId(@Param("syncUserId") String syncUserId, @Param("userState") String userState);

    /**
     * 功能描述： 验证当前用户是否已经注册过
     *
     * @param userName   当前用户的流水ID
     * @param syncUserId 当前用户所属系统流水ID
     * @return
     */
    int checkUserIsRegister(@Param("userName") String userName, @Param("syncUserId") String syncUserId);

    /**
     * 功能描述： 查询好友
     *
     * @param userNo   用户编号
     * @param nickName 用户昵称
     * @return 查询好友的数据
     */
    List<QueryFriendVo> queryFriend(@Param("userNo") String userNo, @Param("nickName") String nickName);

}