package com.github.lazyboyl.chat.core.service;

import com.github.lazyboyl.chat.core.auth.OnlineChatInitialization;
import com.github.lazyboyl.chat.core.constant.FriendStateEnum;
import com.github.lazyboyl.chat.core.constant.MsgTypeEnum;
import com.github.lazyboyl.chat.core.constant.ReceiveMessageState;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.*;
import com.github.lazyboyl.chat.core.entity.*;
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
import java.util.List;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： websocket的service类
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class WebsocketChatService {

    /**
     * 获取当前登录用户的相关操作的实现
     */
    @Autowired
    private OnlineChatInitialization onlineChatInitialization;

    /**
     * 注入好友的dao
     */
    @Autowired
    private FriendDao friendDao;

    /**
     * 注入聊天的dao
     */
    @Autowired
    private ChatMessageDao chatMessageDao;

    /**
     * 注入消息接收的dao
     */
    @Autowired
    private ReceiveMessageDao receiveMessageDao;

    /**
     * 注入用户的dao
     */
    @Autowired
    private ChatUserDao chatUserDao;

    /**
     * 注入群组的dao
     */
    @Autowired
    private GroupDao groupDao;

    /**
     * 注入群组成员的dao
     */
    @Autowired
    private GroupMemberDao groupMemberDao;

    /**
     * 功能描述： 实现发送群消息
     *
     * @param token          当前登录的用户的token
     * @param ctx            通道对象
     * @param groupId        群流水ID
     * @param uniqueNo       请求唯一标识
     * @param messageContent 消息内容
     * @return 返回发送结果
     */
    public ReturnInfo sendGroupMessage(String token, ChannelHandlerContext ctx, String groupId, String uniqueNo, String messageContent) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUserByToken(token);
        Map<String, Object> uniqueMap = new HashMap<>();
        uniqueMap.put("uniqueNo", uniqueNo);
        Map<String, Object> data;
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "token无效！", uniqueMap);
        } else {
            Group group = groupDao.selectByPrimaryKey(groupId);
            if (group == null) {
                return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此群组记录！");
            }
            if (groupMemberDao.isUserInGroup(groupId, chatUser.getUserId()) == 0) {
                return new ReturnInfo(SystemEnum.FAIL.getKey(), "您已经被移出当前群了，无法发送消息！");
            }
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageContent(messageContent);
            chatMessage.setMessageType("2");
            chatMessage.setCrtDate(new Date());
            chatMessage.setCrtUserId(chatUser.getUserId());
            chatMessage.setCrtUserName(chatUser.getUserName());
            chatMessageDao.insertSelective(chatMessage);
            ReceiveMessage receiveMessage = new ReceiveMessage();
            receiveMessage.setChatMessageId(chatMessage.getChatMessageId());
            receiveMessage.setReceiveDate(new Date());
            receiveMessage.setSendUserId(chatUser.getUserId());
            receiveMessage.setGroupId(groupId);
            receiveMessageDao.insertSelective(receiveMessage);
            // 实现群的消息的推送
            List<GroupMember> groupMemberList = groupMemberDao.getGroupMember(groupId, chatUser.getUserId());
            Channel channel;
            for (GroupMember gm : groupMemberList) {
                channel = ChatLoginData.getLoginChannel(gm.getUserId());
                if (channel != null) {
                    data = new HashMap<>();
                    data.put("sendUserId", chatUser.getUserId());
                    data.put("messageContent", messageContent);
                    data.put("receiveDate", receiveMessage.getReceiveDate());
                    data.put("groupId", groupId);
                    CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.GROUPMSG.getType(), data));
                }
            }
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "消息发送成功！", uniqueMap);
    }

    /**
     * 功能描述： 实现消息的发送
     *
     * @param token          当前通讯的token
     * @param ctx            通道对象
     * @param receiveUserId  接收人人的ID
     * @param uniqueNo       此处请求的唯一标识
     * @param messageContent 聊天的消息
     */
    public ReturnInfo sendMessage(String token, ChannelHandlerContext ctx, String receiveUserId, String uniqueNo, String messageContent) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUserByToken(token);
        Map<String, Object> uniqueMap = new HashMap<>();
        uniqueMap.put("uniqueNo", uniqueNo);
        Map<String, Object> data = new HashMap<>();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "token无效！", uniqueMap);
        } else {
            ChatUser chatReceiveUser = chatUserDao.selectByPrimaryKey(receiveUserId);
            if (chatReceiveUser == null) {
                return new ReturnInfo(SystemEnum.FAIL.getKey(), "无此聊天用户！", uniqueMap);
            } else {
                if (friendDao.isYourFriend(chatUser.getUserId(), receiveUserId) == 0) {
                    return new ReturnInfo(SystemEnum.FAIL.getKey(), "双方不是好友无法发送消息！", uniqueMap);
                } else {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setMessageContent(messageContent);
                    chatMessage.setMessageType("1");
                    chatMessage.setCrtDate(new Date());
                    chatMessage.setCrtUserId(chatUser.getUserId());
                    chatMessage.setCrtUserName(chatUser.getUserName());
                    chatMessageDao.insertSelective(chatMessage);
                    ReceiveMessage receiveMessage = new ReceiveMessage();
                    receiveMessage.setChatMessageId(chatMessage.getChatMessageId());
                    receiveMessage.setReceiveDate(new Date());
                    receiveMessage.setSendUserId(chatUser.getUserId());
                    receiveMessage.setReceiveUserId(receiveUserId);
                    receiveMessage.setMsgState(ReceiveMessageState.WAIT_READ.getMsgState());
                    receiveMessageDao.insertSelective(receiveMessage);
                    // 推送消息给接收人
                    Channel channel = ChatLoginData.getLoginChannel(receiveUserId);
                    if (channel != null) {
                        data.put("sendUserId", chatUser.getUserId());
                        data.put("receiveUserId", receiveUserId);
                        data.put("messageContent", messageContent);
                        data.put("receiveDate", receiveMessage.getReceiveDate());
                        CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.USERMSG.getType(), data));
                    }
                }
            }
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "消息发送成功！", uniqueMap);
    }

    /**
     * 功能描述： 实现用户的登录
     *
     * @param token 当前登录的token
     * @param ctx   当前连接的socket的对象
     */
    public void login(String token, ChannelHandlerContext ctx) {
        ChatUser chatUser = onlineChatInitialization.getLoginChatUserByToken(token);
        Map<String, Object> data = new HashMap<>();
        if (chatUser == null) {
            data.put("code", SystemEnum.FAIL.getKey());
            data.put("msg", "token无效登录操作失败");
        } else {
            // 将当前的通道信息和用户ID进行映射保存
            ChatLoginData.addChannel(ctx, chatUser.getUserId());
            // 更新好友为在线状态
            friendDao.friendOnOff(chatUser.getUserId(), FriendStateEnum.ONLINE.getFriendState());
            data.put("code", SystemEnum.SUCCESS.getKey());
            data.put("msg", "登录成功");
        }
        CtxWriteUtil.writeAndFlush(ctx.channel(), new WebsocketMsgVo(MsgTypeEnum.LOGIN.getType(), data));
    }

}
