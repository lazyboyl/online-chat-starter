package com.github.lazyboyl.chat.core.websocket.security;

import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.websocket.security.WebsocketSecurity;
import com.github.lazyboyl.websocket.server.channel.entity.SocketRequest;
import com.github.lazyboyl.websocket.server.channel.entity.SocketResponse;
import com.github.lazyboyl.websocket.util.SocketUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 实现websocket的请求鉴权
 */
public class WebsocketSecurityImpl implements WebsocketSecurity {
    @Override
    public int level() {
        return 0;
    }

    @Override
    public Boolean authentication(ChannelHandlerContext ctx, SocketRequest socketRequest) {
        if("/chat/login/".equals(socketRequest.getUrl())){
            return true;
        }
        if(ChatLoginData.auth(ctx)){
            return true;
        }
        SocketUtil.writeAndFlush(ctx.channel(), new SocketResponse(SystemEnum.FAIL.getKey(), "非法访问！"));
        return false;
    }
}
