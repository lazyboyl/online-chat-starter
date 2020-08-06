package com.github.lazyboyl.chat.core.controller;

import com.github.lazyboyl.chat.core.entity.ReturnInfo;
import com.github.lazyboyl.chat.core.service.GroupManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linzf
 * @since 2020/8/6
 * 类描述： 群组维护的类
 */
@RestController
@RequestMapping("/group")
public class GroupManagerController {

    /**
     * 注入群组维护的service
     */
    @Autowired
    private GroupManagerService groupManagerService;

    /**
     * 功能描述： 用户审核邀请入群
     *
     * @param applyGroupId 邀请入群流水ID
     * @param applyState   审核状态【0：拒绝；9：通过】
     * @param note         审核理由
     * @return 返回审核结果
     */
    @PostMapping("userVerify")
    public ReturnInfo userVerify(@RequestParam(name = "applyGroupId") String applyGroupId,
                                 @RequestParam(name = "applyState") String applyState,
                                 @RequestParam(name = "note") String note){
        return groupManagerService.userVerify(applyGroupId, applyState, note);
    }

    /**
     * 功能描述： 群主审核入群申请
     *
     * @param applyGroupId 入群申请流水ID
     * @param applyState   审核状态【0：拒绝；9：通过】
     * @param note         审核理由
     * @return 返回审核结果
     */
    @PostMapping("groupVerify")
    public ReturnInfo groupVerify(@RequestParam(name = "applyGroupId") String applyGroupId,
                                  @RequestParam(name = "applyState") String applyState,
                                  @RequestParam(name = "note") String note) {
        return groupManagerService.groupVerify(applyGroupId, applyState, note);
    }


    /**
     * 功能描述： 申请入群的note
     *
     * @param groupId 申请进入的群
     * @param note    伸进入群的消息
     * @return 返回申请入群结果
     */
    @PostMapping("applyGroup")
    public ReturnInfo applyGroup(@RequestParam(name = "groupId") String groupId,
                                 @RequestParam(name = "note") String note) {
        return groupManagerService.applyGroup(groupId, note);
    }

    /**
     * 功能描述： 邀请入群
     *
     * @param groupId 群流水ID
     * @param userId  邀请人流水ID
     * @param note    邀请备注
     * @return 返回邀请结果
     */
    @PostMapping("invitationGroup")
    public ReturnInfo invitationGroup(@RequestParam(name = "groupId") String groupId,
                                      @RequestParam(name = "userId") String userId,
                                      @RequestParam(name = "note") String note) {
        return groupManagerService.invitationGroup(groupId, userId, note);
    }

    /**
     * 功能描述： 实现删除群组
     *
     * @param groupId 群组流水ID
     * @return 返回删除结果
     */
    @PostMapping("deleteGroup")
    public ReturnInfo deleteGroup(@RequestParam(name = "groupId") String groupId) {
        return groupManagerService.deleteGroup(groupId);
    }

    /**
     * 功能描述： 更新群组信息
     *
     * @param groupId   群组流水ID
     * @param groupName 群组名称
     * @param groupImg  群组图标
     * @return 返回更新结果
     */
    @PostMapping("updateGroup")
    public ReturnInfo updateGroup(@RequestParam(name = "groupId") String groupId,
                                  @RequestParam(name = "groupName") String groupName,
                                  @RequestParam(name = "groupImg") String groupImg) {
        return groupManagerService.updateGroup(groupId, groupName, groupImg);
    }

    /**
     * 功能描述： 获取群组信息
     *
     * @param groupId 群组流水ID
     * @return 返回查询结果
     */
    @PostMapping("getGroup")
    public ReturnInfo getGroup(@RequestParam(name = "groupId") String groupId) {
        return groupManagerService.getGroup(groupId);
    }

    /**
     * 功能描述： 增加群组
     *
     * @param groupName 群组名称
     * @param groupImg  群组图标
     * @return 返回增加结果
     */
    @PostMapping("addGroup")
    public ReturnInfo addGroup(@RequestParam(name = "groupName") String groupName,
                               @RequestParam(name = "groupImg") String groupImg) {
        return groupManagerService.addGroup(groupName, groupImg);
    }

}
