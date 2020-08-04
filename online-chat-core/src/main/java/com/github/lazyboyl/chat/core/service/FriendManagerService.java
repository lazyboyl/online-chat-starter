package com.github.lazyboyl.chat.core.service;

import com.alibaba.druid.util.StringUtils;
import com.github.lazyboyl.chat.core.auth.UserLoginAuthService;
import com.github.lazyboyl.chat.core.constant.ApplyStateEnum;
import com.github.lazyboyl.chat.core.constant.MsgTypeEnum;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.ApplyFriendDao;
import com.github.lazyboyl.chat.core.dao.ChatUserDao;
import com.github.lazyboyl.chat.core.dao.FriendDao;
import com.github.lazyboyl.chat.core.dao.FriendGroupDao;
import com.github.lazyboyl.chat.core.entity.ApplyFriend;
import com.github.lazyboyl.chat.core.entity.ChatUser;
import com.github.lazyboyl.chat.core.entity.ReturnInfo;
import com.github.lazyboyl.chat.core.util.CtxWriteUtil;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.chat.core.websocket.entity.WebsocketMsgVo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
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
    private UserLoginAuthService userLoginAuthService;

    /**
     * 功能描述： 实现好友的申请
     *
     * @param userId 申请的好友的流水ID
     * @param note   申请信息
     * @return 返回申请结果
     */
    public ReturnInfo applyFriend(String userId, String note) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
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
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "", friendGroupDao.myFriendList(chatUser.getUserId()));
    }

}
