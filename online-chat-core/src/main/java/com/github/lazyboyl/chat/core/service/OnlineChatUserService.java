package com.github.lazyboyl.chat.core.service;

import com.github.lazyboyl.chat.core.constant.AllowDeletionEnum;
import com.github.lazyboyl.chat.core.constant.ChatUserStateEnum;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.ChatUserDao;
import com.github.lazyboyl.chat.core.dao.FriendGroupDao;
import com.github.lazyboyl.chat.core.entity.ChatUser;
import com.github.lazyboyl.chat.core.entity.FriendGroup;
import com.github.lazyboyl.chat.core.entity.ReturnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author linzf
 * @since 2020/8/10
 * 类描述： 用户管理的service
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class OnlineChatUserService {

    /**
     * 注入用户的dao
     */
    @Autowired
    private ChatUserDao chatUserDao;

    /**
     * 注入好友分组的dao
     */
    @Autowired
    private FriendGroupDao friendGroupDao;

    /**
     * 功能描述： 实现数据的同步
     *
     * @param chatUserList 当前需要同步的用户数据的集合
     * @return 返回同步结果
     */
    public ReturnInfo initChatUser(List<ChatUser> chatUserList) {
        for (ChatUser cu : chatUserList) {
            addChatUser(cu);
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "数据同步成功！");
    }

    /**
     * 功能描述： 实现更新用户信息
     *
     * @param chatUser 待更新的用户信息
     * @return 返回更新结果
     */
    public ReturnInfo updateChatUser(ChatUser chatUser) {
        ChatUser nowUser = chatUserDao.queryChatUserBySyncUserId(chatUser.getSyncUserId());
        if (nowUser == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此用户！");
        }
        nowUser.setNickName(chatUser.getNickName());
        nowUser.setAvatar(chatUser.getAvatar());
        chatUserDao.updateByPrimaryKeySelective(nowUser);
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "更新用户成功！");
    }

    /**
     * 功能描述： 删除用户
     *
     * @param syncUserId 外部流水ID
     * @return 返回删除结果
     */
    public ReturnInfo deleteChatUser(String syncUserId) {
        ChatUser chatUser = chatUserDao.queryChatUserBySyncUserId(syncUserId);
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此用户，删除失败！");
        }
        chatUser.setUserState(ChatUserStateEnum.CANCEL.getUserState());
        chatUserDao.updateByPrimaryKeySelective(chatUser);
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "删除用户成功！");
    }

    /**
     * 功能描述： 新增用户
     *
     * @param chatUser 当前需要新增的用户
     * @return 返回新增结果
     */
    public ReturnInfo addChatUser(ChatUser chatUser) {
        ChatUser nowUser = chatUserDao.queryChatUserBySyncUserId(chatUser.getSyncUserId());
        if (nowUser == null) {
            // 新增用户
            chatUser.setCrtDate(new Date());
            chatUser.setUserState(ChatUserStateEnum.NORMAL.getUserState());
            chatUserDao.insertSelective(chatUser);
            // 新增默认分组信息
            FriendGroup friendGroup = new FriendGroup();
            friendGroup.setFriendGroupOrder(1);
            friendGroup.setFriendGroupName("我的好友");
            friendGroup.setAllowDeletion(AllowDeletionEnum.NOT_ALLOW.getAllowDeletion());
            friendGroup.setCrtUserId(chatUser.getUserId());
            friendGroupDao.insertSelective(friendGroup);
            // 更新用户所属默认分组
            chatUser.setDefaultGroupId(friendGroup.getFriendGroupId());
            chatUserDao.updateByPrimaryKeySelective(chatUser);
        } else {
            if (nowUser.getUserState().equals(ChatUserStateEnum.NORMAL.getUserState())) {
                return new ReturnInfo(SystemEnum.FAIL.getKey(), "当前用户已注册过无需再次注册！");
            }
            nowUser.setUserState(ChatUserStateEnum.NORMAL.getUserState());
            chatUserDao.updateByPrimaryKeySelective(chatUser);
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "新增用户成功！");
    }

}
