package com.github.lazyboyl.chat.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 实时聊天的service
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class LiveChatService {
}
