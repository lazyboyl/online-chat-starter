package com.github.lazyboyl.chat.core.util;

import com.github.lazyboyl.websocket.util.JsonUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述：
 */
public class CtxWriteUtil {

    /**
     * 功能描述： 像前端推送信息
     * @param channel 当前的通道
     * @param obj 返回的结果
     */
    public static void writeAndFlush(Channel channel, Object obj) {
        channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objToJson(obj)));
    }

}
