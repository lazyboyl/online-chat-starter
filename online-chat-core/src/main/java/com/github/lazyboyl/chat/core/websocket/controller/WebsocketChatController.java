package com.github.lazyboyl.chat.core.websocket.controller;

import com.github.lazyboyl.chat.core.entity.ReturnInfo;
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
     * 功能描述： 实现发送群消息
     *
     * @param token          当前登录的用户的token
     * @param ctx            通道对象
     * @param groupId        群流水ID
     * @param uniqueNo       请求唯一标识
     * @param messageContent 消息内容
     * @return 返回发送结果
     */
    @WebSocketRequestMapping("sendGroupMessage")
    public ReturnInfo sendGroupMessage(String token, ChannelHandlerContext ctx, String groupId, String uniqueNo, String messageContent) {
        return websocketChatService.sendGroupMessage(token, ctx, groupId, uniqueNo, messageContent);
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
    @WebSocketRequestMapping("sendMessage")
    public ReturnInfo sendMessage(String token, ChannelHandlerContext ctx, String receiveUserId, String uniqueNo, String messageContent) {
        return websocketChatService.sendMessage(token, ctx, receiveUserId, uniqueNo, messageContent);
    }

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
