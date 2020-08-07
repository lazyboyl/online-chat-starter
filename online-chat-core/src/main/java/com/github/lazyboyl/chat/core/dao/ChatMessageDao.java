package com.github.lazyboyl.chat.core.dao;

import com.github.lazyboyl.chat.core.entity.ChatMessage;
import com.github.lazyboyl.chat.core.vo.LoadMoreMessageVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author linzef
 * @since 2020-07-19
 * 类描述：
 */
public interface ChatMessageDao extends Mapper<ChatMessage> {

    /**
     * 功能描述：查询群组更多消息
     *
     * @param groupId 群组流水ID
     * @return 返回查询结果
     */
    List<LoadMoreMessageVo> loadGroupMoreMessage(@Param("groupId") String groupId);

    /**
     * 功能描述： 查看更多消息
     *
     * @param sendUserId    发送消息的人的流水ID
     * @param receiveUserId 接收人的流水ID
     * @return 返回查询的结果
     */
    List<LoadMoreMessageVo> loadMoreMessage(@Param("sendUserId") String sendUserId, @Param("receiveUserId") String receiveUserId);

}