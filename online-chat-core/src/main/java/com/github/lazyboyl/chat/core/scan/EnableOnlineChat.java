package com.github.lazyboyl.chat.core.scan;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author linzef
 * @since 2020-08-10
 * 注解描述： 实现开启在线聊天的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({OnlineChatScannerRegister.class})
public @interface EnableOnlineChat {

    String[] basePackages() default {"com.github.lazyboyl.chat.core"};

}
