package com.github.lazyboyl.chat.core.service;

import com.github.lazyboyl.chat.core.auth.UserLoginAuthService;
import com.github.lazyboyl.chat.core.constant.FriendStateEnum;
import com.github.lazyboyl.chat.core.constant.MsgTypeEnum;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.FriendDao;
import com.github.lazyboyl.chat.core.entity.ChatUser;
import com.github.lazyboyl.chat.core.util.CtxWriteUtil;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.chat.core.websocket.entity.WebsocketMsgVo;
import com.github.lazyboyl.websocket.server.channel.entity.SocketResponse;
import com.github.lazyboyl.websocket.util.SocketUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： websocket的service类
 */
@Service
public class WebsocketChatService {

    /**
     * 获取当前登录用户的相关操作的实现
     */
    @Autowired
    private UserLoginAuthService userLoginAuthService;

    /**
     * 注入好友的dao
     */
    @Autowired
    private FriendDao friendDao;

    /**
     * 功能描述： 实现用户的登录
     *
     * @param token 当前登录的token
     * @param ctx   当前连接的socket的对象
     */
    public void login(String token, ChannelHandlerContext ctx) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUserByToken(token);
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
        CtxWriteUtil.writeAndFlush(ctx.channel(),new WebsocketMsgVo(MsgTypeEnum.LOGIN.getType(), data));
    }

}
