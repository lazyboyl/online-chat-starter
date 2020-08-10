package com.github.lazyboyl.chat.core.config;

import com.github.lazyboyl.websocket.integrate.EnableWebSocketServer;
import org.springframework.stereotype.Component;

/**
 * @author linzf
 * @since 2020/8/10
 * 类描述：
 */
@EnableWebSocketServer(webSocketScanPackage = "com.github.lazyboyl.chat.core.websocket")
@Component
public class WebsocketConfig {
}
