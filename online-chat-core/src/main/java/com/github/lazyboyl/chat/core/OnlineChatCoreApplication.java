package com.github.lazyboyl.chat.core;

import com.github.lazyboyl.websocket.integrate.EnableWebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author linzef
 */
@SpringBootApplication
@EnableWebSocketServer(webSocketScanPackage = "com.github.lazyboyl.chat.core.controller")
public class OnlineChatCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineChatCoreApplication.class, args);
    }

}
