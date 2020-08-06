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
     * 功能描述： 获取我的好友的分组列表的数据
     *
     * @return 返回查询结果
     */
    @PostMapping("getFriendGroupList")
    public ReturnInfo getFriendGroupList() {
        return friendManagerService.getFriendGroupList();
    }

    /**
     * 功能描述： 查看更多消息
     *
     * @param userId   当前的聊天的用户的ID
     * @param page     查看消息的页数
     * @param pageSize 每页加载的消息数
     * @return 返回查询结果
     */
    @PostMapping("loadMoreMessage")
    public ReturnInfo loadMoreMessage(@RequestParam(name = "userId") String userId,
                                      @RequestParam(name = "page") Integer page,
                                      @RequestParam(name = "pageSize") Integer pageSize) {
        return friendManagerService.loadMoreMessage(userId, page, pageSize);
    }

    /**
     * 功能描述： 获取好友请求列表
     *
     * @return 返回获取结果
     */
    @PostMapping("getApplyFriendList")
    public ReturnInfo getApplyFriendList() {
        return friendManagerService.getApplyFriendList();
    }

    /**
     * 功能描述： 删除好友分组的时候实现分组底下好友的数据的迁移
     *
     * @param friendGroupId       待删除的好友分组
     * @param targetFriendGroupId 待迁移的好友的分组数据
     * @return 返回删除操作结果
     */
    @PostMapping("deleteFriendGroup")
    public ReturnInfo deleteFriendGroup(@RequestParam(name = "friendGroupId") String friendGroupId,
                                        @RequestParam(name = "targetFriendGroupId") String targetFriendGroupId) {
        return friendManagerService.deleteFriendGroup(friendGroupId, targetFriendGroupId);
    }


    /**
     * 功能描述： 创建分组
     *
     * @param friendGroupName  分组名称
     * @param friendGroupOrder 分组顺序
     * @return 返回创建结果
     */
    @PostMapping("createFriendGroup")
    public ReturnInfo createFriendGroup(@RequestParam(name = "friendGroupName") String friendGroupName,
                                        @RequestParam(name = "friendGroupOrder") int friendGroupOrder) {
        return friendManagerService.createFriendGroup(friendGroupName, friendGroupOrder);
    }

    /**
     * 功能描述： 删除好友
     *
     * @param friendId 待删除的好友ID
     * @return 返回删除的结果
     */
    @PostMapping("deleteFriend")
    public ReturnInfo deleteFriend(@RequestParam(name = "friendId") String friendId) {
        return friendManagerService.deleteFriend(friendId);
    }

    /**
     * 功能描述： 好友申请审核
     *
     * @param applyFriendId 好友申请审核流水ID
     * @param friendGroupId 好友分组所在流水ID
     * @param applyState    审核状态 【0：拒绝；9：通过】
     * @return
     */
    @PostMapping("verifyFriend")
    public ReturnInfo verifyFriend(@RequestParam(name = "applyFriendId") String applyFriendId,
                                   @RequestParam(name = "friendGroupId") String friendGroupId,
                                   @RequestParam(name = "applyState") String applyState) {
        return friendManagerService.verifyFriend(applyFriendId, friendGroupId, applyState);
    }

    /**
     * 功能描述： 实现好友的申请
     *
     * @param userId        申请的好友的流水ID
     * @param note          申请信息
     * @param friendGroupId 分组流水ID
     * @param remark        备注
     * @return 返回申请结果
     */
    @PostMapping("applyFriend")
    public ReturnInfo applyFriend(@RequestParam(name = "userId") String userId,
                                  @RequestParam(name = "note") String note,
                                  @RequestParam(name = "friendGroupId") String friendGroupId,
                                  @RequestParam(name = "remark") String remark) {
        return friendManagerService.applyFriend(userId, note, friendGroupId, remark);
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
        return friendManagerService.queryFriend(userNo, nickName);
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
