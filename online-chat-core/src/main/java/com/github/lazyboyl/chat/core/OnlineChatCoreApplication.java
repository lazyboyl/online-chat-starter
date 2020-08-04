package com.github.lazyboyl.chat.core;

import com.github.lazyboyl.websocket.integrate.EnableWebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author linzef
 */
@SpringBootApplication
@EnableWebSocketServer(webSocketScanPackage = "com.github.lazyboyl.chat.core.websocket")
@MapperScan("com.github.lazyboyl.chat.core.dao")
public class OnlineChatCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineChatCoreApplication.class, args);
    }

}
