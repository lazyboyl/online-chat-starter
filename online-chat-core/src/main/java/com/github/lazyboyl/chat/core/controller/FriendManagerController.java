package com.github.lazyboyl.chat.core.controller;

import com.github.lazyboyl.chat.core.entity.ReturnInfo;
import com.github.lazyboyl.chat.core.service.FriendManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 好友维护的controller
 */
@RestController
@RequestMapping("/friend")
public class FriendManagerController {

    /**
     * 注入好友维护的service
     */
    @Autowired
    private FriendManagerService friendManagerService;

    /**
     * 功能描述： 实现好友的申请
     *
     * @param userId 申请的好友的流水ID
     * @param note 申请信息
     * @return 返回申请结果
     */
    @PostMapping("applyFriend")
    public ReturnInfo applyFriend(@RequestParam(name = "userId") String userId,@RequestParam(name = "note")  String note){
        return friendManagerService.applyFriend(userId,note);
    }

    /**
     * 功能描述： 查询好友
     *
     * @param userNo   用户编号
     * @param nickName 用户昵称
     * @return 查询好友的数据
     */
    @PostMapping("queryFriend")
    public ReturnInfo queryFriend(@RequestParam(name = "userNo") String userNo, @RequestParam(name = "nickName") String nickName) {
        return friendManagerService.queryFriend(userNo,nickName);
    }

    /**
     * 功能描述： 获取我的好友列表
     *
     * @return 我的好友列表的数据
     */
    @PostMapping("myFriendList")
    public ReturnInfo myFriendList() {
        return friendManagerService.myFriendList();
    }

}
