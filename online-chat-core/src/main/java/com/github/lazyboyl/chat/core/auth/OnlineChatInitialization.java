package com.github.lazyboyl.chat.core.auth;

import com.github.lazyboyl.chat.core.entity.ChatUser;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述：
 */
public interface OnlineChatInitialization {

    /**
     * 功能描述： 获取当前登录的用户信息
     * @return ChatUser 返回当前登录的用户的信息
     */
    ChatUser getLoginChatUser();

    /**
     * 功能描述： 根据token来获取当前登录的用户的信息
     * @param token 当前的token的值
     * @return 返回当前登录的用户的信息
     */
    ChatUser getLoginChatUserByToken(String token);

}
