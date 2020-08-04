package com.github.lazyboyl.chat.core.websocket.controller;

import com.github.lazyboyl.chat.core.service.WebsocketChatService;
import com.github.lazyboyl.websocket.annotation.WebSocketController;
import com.github.lazyboyl.websocket.annotation.WebSocketRequestMapping;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： websocket进行数据交互的controller
 */
@WebSocketController
@WebSocketRequestMapping("/chat/")
public class WebsocketChatController {

    @Autowired
    private WebsocketChatService websocketChatService;

    /**
     * 功能描述： 实现用户的登录
     *
     * @param token 当前登录的token
     * @param ctx   当前连接的socket的对象
     */
    @WebSocketRequestMapping("login")
    public void login(String token, ChannelHandlerContext ctx) {
        websocketChatService.login(token, ctx);
    }

}
