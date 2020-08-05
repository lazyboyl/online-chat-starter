package com.github.lazyboyl.chat.core.websocket.data;

import com.alibaba.druid.util.StringUtils;
import com.github.lazyboyl.chat.core.constant.FriendStateEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 当前登录的聊天用户的键值映射
 */
public class ChatLoginData {

    /**
     * 当前登录的用户的键值映射
     */
    private static Map<String, Channel> loginChannelMap = new HashMap<>();

    /**
     * 当前与服务器连接的channel的ID和channel的键值映射
     */
    private static Map<String, Channel> loginChannelIdMap = new HashMap<>();

    /**
     * 当前与服务器连接的channel的ID和当前登录用户的映射
     */
    private static Map<String, String> channelIDMapUserId = new HashMap<>();

    /**
     * 功能描述： 根据当前登录的用户的ID来获取通道信息
     *
     * @param userId 用户流水ID
     * @return 返回通道信息
     */
    public static Channel getLoginChannel(String userId) {
        return loginChannelMap.get(userId);
    }

    /**
     * 功能描述： 实现新增channel到map中
     *
     * @param ctx    当前的通道对象
     * @param userId 当前登录的用户ID
     */
    public static void addChannel(ChannelHandlerContext ctx, String userId) {
        loginChannelMap.put(userId, ctx.channel());
        loginChannelIdMap.put(ctx.channel().id().asLongText(), ctx.channel());
        channelIDMapUserId.put(ctx.channel().id().asLongText(), userId);
    }

    /**
     * 功能描述： 当用户断开连接的时候移除相关的数据
     *
     * @param ctx
     */
    public static void removeChannel(ChannelHandlerContext ctx) {
        String userId = channelIDMapUserId.get(ctx.channel().id().asLongText());
        if (!StringUtils.isEmpty(userId)) {
            channelIDMapUserId.remove(ctx.channel().id().asLongText());
            loginChannelIdMap.remove(ctx.channel().id().asLongText());
            loginChannelMap.remove(userId);
        }
    }

    /**
     * 功能描述： 判断当前的socket请求的通道是否已经注册成功
     *
     * @param ctx 当前的请求通道
     * @return false：未注册；true：已注册
     */
    public static Boolean auth(ChannelHandlerContext ctx) {
        if (loginChannelIdMap.get(ctx.channel().id().asLongText()) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能描述： 获取当前登录的用户的流水ID
     *
     * @param ctx 通道对象
     * @return 返回当前登录用户的流水ID
     */
    public static String getUserId(ChannelHandlerContext ctx) {
        return channelIDMapUserId.get(ctx.channel().id().asLongText());
    }

    /**
     * 功能描述： 判断当前学生是否在线
     *
     * @param userId 用户的流水ID
     * @return 返回验证结果
     */
    public static String checkUserIsOnline(String userId) {
        if (loginChannelMap.get(userId) != null) {
            return FriendStateEnum.ONLINE.getFriendState();
        } else {
            return FriendStateEnum.OFFLINE.getFriendState();
        }
    }

}
