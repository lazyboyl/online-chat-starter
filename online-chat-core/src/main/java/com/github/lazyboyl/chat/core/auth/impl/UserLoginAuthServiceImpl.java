package com.github.lazyboyl.chat.core.auth.impl;

import com.github.lazyboyl.chat.core.auth.UserLoginAuthService;
import com.github.lazyboyl.chat.core.entity.ChatUser;
import org.springframework.stereotype.Component;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述：
 */
@Component
public class UserLoginAuthServiceImpl implements UserLoginAuthService {

    @Override
    public ChatUser getLoginChatUser() {
        ChatUser chatUser = new ChatUser();
        chatUser.setUserId("1");
        return chatUser;
    }

    @Override
    public ChatUser getLoginChatUserByToken(String token){
        ChatUser chatUser = new ChatUser();
        chatUser.setUserId("1");
        return chatUser;
    }
}
