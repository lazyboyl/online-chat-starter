package com.github.lazyboyl.chat.core.websocket.listener;

import com.github.lazyboyl.chat.core.constant.FriendStateEnum;
import com.github.lazyboyl.chat.core.dao.FriendDao;
import com.github.lazyboyl.chat.core.service.FriendManagerService;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.websocket.listenter.WebSocketHandlerListenter;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 监听当用户连接或者退出的时候的响应事件
 */
public class WebSocketHandlerListener implements WebSocketHandlerListenter {

    @Autowired
    private FriendDao friendDao;

    @Override
    public int level() {
        return 0;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 更新好友为不在线状态
        friendDao.friendOnOff(ChatLoginData.getUserId(ctx), FriendStateEnum.OFFLINE.getFriendState());
        ChatLoginData.removeChannel(ctx);
    }

    @Override
    public void handleShake(ChannelHandlerContext ctx) {

    }
}
