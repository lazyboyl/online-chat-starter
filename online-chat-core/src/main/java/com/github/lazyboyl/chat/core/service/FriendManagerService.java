package com.github.lazyboyl.chat.core.service;

import com.alibaba.druid.util.StringUtils;
import com.github.lazyboyl.chat.core.auth.OnlineChatInitialization;
import com.github.lazyboyl.chat.core.constant.AllowDeletionEnum;
import com.github.lazyboyl.chat.core.constant.ApplyStateEnum;
import com.github.lazyboyl.chat.core.constant.MsgTypeEnum;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.*;
import com.github.lazyboyl.chat.core.entity.*;
import com.github.lazyboyl.chat.core.util.CtxWriteUtil;
import com.github.lazyboyl.chat.core.util.PageUtil;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.chat.core.websocket.entity.WebsocketMsgVo;
import com.github.pagehelper.PageHelper;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 好友维护的service
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class FriendManagerService {

    /**
     * 注入好友分组的dao
     */
    @Autowired
    private FriendGroupDao friendGroupDao;

    /**
     * 注入用户的dao
     */
    @Autowired
    private ChatUserDao chatUserDao;

    /**
     * 注入好友的dao
     */
    @Autowired
    private FriendDao friendDao;

    /**
     * 注入好友申请的dao
     */
    @Autowired
    private ApplyFriendDao applyFriendDao;

    /**
     * 获取当前登录用户的相关操作的实现
     */
    @Autowired
    private OnlineChatInitialization onlineChatInitialization;

    /**
     * 聊天的实体的dao
     */
    @Autowired
    private ChatMessageDao chatMessageDao;

    /**
     * 功能描述： 获取我的好友的分组列表的数据
     *
     * @return 返回查询结果
     */
    public ReturnInfo getFriendGroupList() {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "查看更多消息成功！", friendGroupDao.getFriendGroupList(chatUser.getUserId()));
    }

    /**
     * 功能描述： 查看更多消息
     *
     * @param userId   当前的聊天的用户的ID
     * @param page     查看消息的页数
     * @param pageSize 每页加载的消息数
     * @return 返回查询结果
     */
    public ReturnInfo loadMoreMessage(String userId, Integer page, Integer pageSize) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        PageHelper.startPage(page, (pageSize > 0 && pageSize <= 500) ? pageSize : 20);
        HashMap<String, Object> res = PageUtil.getResult(chatMessageDao.loadMoreMessage(chatUser.getUserId(), userId));
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "查看更多消息成功！", res.get("rows"));
    }

    /**
     * 功能描述： 获取好友请求列表
     *
     * @return 返回获取结果
     */
    public ReturnInfo getApplyFriendList() {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "获取好友请求列表！", applyFriendDao.getApplyFriendList(chatUser.getUserId()));
    }

    /**
     * 功能描述： 删除好友分组的时候实现分组底下好友的数据的迁移
     *
     * @param friendGroupId       待删除的好友分组
     * @return 返回删除操作结果
     */
    public ReturnInfo deleteFriendGroup(String friendGroupId) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        friendGroupDao.deleteByPrimaryKey(friendGroupId);
        friendDao.transferFriendGroup(chatUser.getDefaultGroupId(), friendGroupId, chatUser.getUserId());
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "删除好友分组成功！");
    }

    /**
     * 功能描述： 创建分组
     *
     * @param friendGroupName 分组名称
     * @return 返回创建结果
     */
    public ReturnInfo createFriendGroup(String friendGroupName) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setAllowDeletion(AllowDeletionEnum.ALLOW_DELETE.getAllowDeletion());
        friendGroup.setFriendGroupName(friendGroupName);
        friendGroup.setFriendGroupOrder(friendGroupDao.getMaxFriendGroupOrder(chatUser.getUserId()) + 1);
        friendGroup.setCrtUserId(chatUser.getUserId());
        friendGroupDao.insertSelective(friendGroup);
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "创建好友分组成功！", friendGroup);
    }

    /**
     * 功能描述： 删除好友
     *
     * @param friendId 待删除的好友ID
     * @return 返回删除的结果
     */
    public ReturnInfo deleteFriend(String friendId) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        Friend friend = friendDao.selectByPrimaryKey(friendId);
        if (friend != null) {
            // 删除两边的好友的关联的数据
            friendDao.deleteFriend(friend.getUserId(), friend.getBelowUserId());
            friendDao.deleteFriend(friend.getBelowUserId(), friend.getUserId());
            // 前端相应的用户推送好友审核的消息
            Channel channel = ChatLoginData.getLoginChannel(friend.getUserId());
            if (channel != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("applyUserId", chatUser.getUserId());
                data.put("applyNickName", chatUser.getNickName());
                data.put("applyDate", new Date());
                CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.REMOVEFRIEND.getType(), data));
            }
            return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "删除好友成功！");
        }
        return new ReturnInfo(SystemEnum.FAIL.getKey(), "好友已删除无需再次删除！");
    }

    /**
     * 功能描述： 好友申请审核
     *
     * @param applyFriendId 好友申请审核流水ID
     * @param friendGroupId 好友分组所在流水ID
     * @param applyState    审核状态 【0：拒绝；9：通过】
     * @return
     */
    public ReturnInfo verifyFriend(String applyFriendId, String friendGroupId, String applyState) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        ApplyFriend applyFriend = applyFriendDao.selectByPrimaryKey(applyFriendId);
        if (applyFriend == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "审核失败，无此申请记录！");
        }
        if (!applyFriend.getTargetUserId().equals(chatUser.getUserId())) {
            return new ReturnInfo(SystemEnum.AUTH_FAIL.getKey(), "越权访问！");
        }
        FriendGroup friendGroup = friendGroupDao.selectByPrimaryKey(friendGroupId);
        if (friendGroup == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "分组不存在，请重新选择分组！");
        }
        Date verifyDate = new Date();
        // 更新好友申请记录的状态
        if (applyFriendDao.verifyFriend(applyFriendId, chatUser.getUserId(), applyState, verifyDate) == 0) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "审核失败，无此申请记录！");
        }
        // 若当前的请求为审核通过的数据的处理方式
        if (ApplyStateEnum.PASS.getState().equals(applyState)) {
            ChatUser friendUser = chatUserDao.selectByPrimaryKey(applyFriend.getApplyUserId());
            // 添加当前申请人成为我的好友
            Friend friend = new Friend();
            friend.setAvatar(chatUser.getAvatar());
            friend.setBelowUserId(chatUser.getUserId());
            friend.setFriendGroupId(friendGroupId);
            friend.setNickName(friendUser.getNickName());
            friend.setUserId(friendUser.getUserId());
            friend.setFriendState(ChatLoginData.checkUserIsOnline(friendUser.getUserId()));
            friendDao.insertSelective(friend);
            // 添加我成为申请人的好友
            friend = new Friend();
            friend.setAvatar(friendUser.getAvatar());
            friend.setBelowUserId(friendUser.getUserId());
            friend.setFriendGroupId(applyFriend.getFriendGroupId());
            friend.setNickName(chatUser.getNickName());
            friend.setUserId(chatUser.getUserId());
            friend.setFriendState(ChatLoginData.checkUserIsOnline(chatUser.getUserId()));
        }
        // 前端相应的用户推送好友审核的消息
        Channel channel = ChatLoginData.getLoginChannel(applyFriend.getApplyUserId());
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("applyFriendId", applyFriendId);
            data.put("applyState", applyState);
            data.put("verifyDate", verifyDate);
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.VERIFYFRIEND.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "好友审核操作成功");
    }

    /**
     * 功能描述： 实现好友的申请
     *
     * @param userId 申请的好友的流水ID
     * @param note   申请信息
     * @return 返回申请结果
     */
    public ReturnInfo applyFriend(String userId, String note, String friendGroupId, String remark) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        if (friendDao.isYourFriend(userId, chatUser.getUserId()) > 0) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "当前添加的好友已经是您的好友了，无需再次添加！");
        }
        ApplyFriend applyFriend = applyFriendDao.queryApplyFriendByApplyUserIdAndTargetUserId(chatUser.getUserId(), userId);
        if (applyFriend == null) {
            applyFriend = new ApplyFriend();
            applyFriend.setApplyUserId(chatUser.getUserId());
            applyFriend.setApplyNickName(chatUser.getNickName());
            applyFriend.setTargetUserId(userId);
            applyFriend.setNote(note);
            applyFriend.setApplyDate(new Date());
            applyFriend.setApplyState(ApplyStateEnum.AUDIT.getState());
            applyFriend.setFriendGroupId(friendGroupId);
            applyFriend.setRemark(remark);
            applyFriendDao.insertSelective(applyFriend);
        } else {
            // 当历史数据显示为过期或者拒绝的时候，这时候才再次发送申请
            if (applyFriend.getApplyState().equals(ApplyStateEnum.REFUSE.getState()) || applyFriend.getApplyState().equals(ApplyStateEnum.EXPIRE.getState())) {
                applyFriend = new ApplyFriend();
                applyFriend.setApplyUserId(chatUser.getUserId());
                applyFriend.setApplyNickName(chatUser.getNickName());
                applyFriend.setTargetUserId(userId);
                applyFriend.setNote(note);
                applyFriend.setApplyDate(new Date());
                applyFriend.setFriendGroupId(friendGroupId);
                applyFriend.setRemark(remark);
                applyFriend.setApplyState(ApplyStateEnum.AUDIT.getState());
                applyFriendDao.insertSelective(applyFriend);
            } else {
                return new ReturnInfo(SystemEnum.FAIL.getKey(), "已发送过好友申请了，无需重复发送！");
            }
        }
        // 前端相应的用户推送好友申请的消息
        Channel channel = ChatLoginData.getLoginChannel(userId);
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("applyFriendId", applyFriend.getApplyFriendId());
            data.put("applyUserId", applyFriend.getApplyUserId());
            data.put("applyNickName", applyFriend.getApplyNickName());
            data.put("note", applyFriend.getNote());
            data.put("applyDate", applyFriend.getApplyDate());
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.FRIENDAPPLY.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "添加好友申请成功");
    }

    /**
     * 功能描述： 查询好友
     *
     * @param userNo   用户编号
     * @param nickName 用户昵称
     * @return 查询好友的数据
     */
    public ReturnInfo queryFriend(String userNo, String nickName) {
        if (StringUtils.isEmpty(userNo) && StringUtils.isEmpty(nickName)) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "用户编号和用户昵称不允许同时为空！");
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "", chatUserDao.queryFriend(userNo, nickName));
    }

    /**
     * 功能描述： 获取我的好友列表
     *
     * @return 我的好友列表的数据
     */
    public ReturnInfo myFriendList() {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "", friendGroupDao.myFriendList(chatUser.getUserId()));
    }

}
