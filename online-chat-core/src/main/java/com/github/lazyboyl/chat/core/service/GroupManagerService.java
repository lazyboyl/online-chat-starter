package com.github.lazyboyl.chat.core.service;

import com.github.lazyboyl.chat.core.auth.UserLoginAuthService;
import com.github.lazyboyl.chat.core.constant.ApplyStateEnum;
import com.github.lazyboyl.chat.core.constant.GroupApplyType;
import com.github.lazyboyl.chat.core.constant.MsgTypeEnum;
import com.github.lazyboyl.chat.core.constant.SystemEnum;
import com.github.lazyboyl.chat.core.dao.*;
import com.github.lazyboyl.chat.core.entity.*;
import com.github.lazyboyl.chat.core.util.CtxWriteUtil;
import com.github.lazyboyl.chat.core.util.PageUtil;
import com.github.lazyboyl.chat.core.websocket.data.ChatLoginData;
import com.github.lazyboyl.chat.core.websocket.entity.WebsocketMsgVo;
import com.github.pagehelper.PageHelper;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linzf
 * @since 2020/8/4
 * 类描述： 群组维护的service
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class GroupManagerService {

    /**
     * 获取当前登录用户的相关操作的实现
     */
    @Autowired
    private UserLoginAuthService userLoginAuthService;

    /**
     * 功注入群组的dao
     */
    @Autowired
    private GroupDao groupDao;

    /**
     * 注入群组成员的DAO
     */
    @Autowired
    private GroupMemberDao groupMemberDao;

    /**
     * 注入群组申请的dao
     */
    @Autowired
    private ApplyGroupDao applyGroupDao;

    /**
     * 注入用户的dao
     */
    @Autowired
    private ChatUserDao chatUserDao;

    /**
     * 注入聊天的dao
     */
    @Autowired
    private ChatMessageDao chatMessageDao;

    /**
     * 功能描述： 查询群的消息
     *
     * @param groupId  群流水ID
     * @param page     第几页
     * @param pageSize 每页显示记录
     * @return 返回查询结果
     */
    public ReturnInfo loadMoreMessage(String groupId, Integer page, Integer pageSize) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        // 判断当前用户是否已经加入群组
        if (groupMemberDao.checkUserIsInGroup(groupId, chatUser.getUserId()) == 0) {
            return new ReturnInfo(SystemEnum.AUTH_FAIL.getKey(), "越权访问！");
        }
        PageHelper.startPage(page, (pageSize > 0 && pageSize <= 500) ? pageSize : 20);
        HashMap<String, Object> res = PageUtil.getResult(chatMessageDao.loadGroupMoreMessage(groupId));
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "查看更多消息成功！", res.get("rows"));
    }

    /**
     * 功能描述： 将用户从群组中移除
     *
     * @param groupId      群组ID
     * @param removeUserId 待移除的用户ID
     * @return 返回移除结果
     */
    public ReturnInfo removeUserGroup(String groupId, String removeUserId) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        Group group = groupDao.selectByPrimaryKey(groupId);
        if (group == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此群组记录！");
        }
        Date operateDate = new Date();
        // 判断当前的群组是否属于当前登录的用户，只有管理员才可以删除群组成员
        if (!group.getCrtUserId().equals(chatUser.getUserId())) {
            return new ReturnInfo(SystemEnum.AUTH_FAIL.getKey(), "非法访问！");
        }
        // 将用户从群组中移除
        groupMemberDao.removeUserGroup(groupId, removeUserId, chatUser.getUserId());
        // 推送申请审核结果给到邀请他的群主
        Channel channel = ChatLoginData.getLoginChannel(removeUserId);
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("groupId", groupId);
            data.put("groupName", group.getGroupName());
            data.put("removeDate", operateDate);
            data.put("removeUserId", chatUser.getUserId());
            data.put("removeUserNickName", chatUser.getNickName());
            data.put("avatar", chatUser.getAvatar());
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.REMOVEGROUP.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "移除用户成功！");
    }

    /**
     * 功能描述： 用户审核邀请入群
     *
     * @param applyGroupId 邀请入群流水ID
     * @param applyState   审核状态【0：拒绝；9：通过】
     * @param note         审核理由
     * @return 返回审核结果
     */
    public ReturnInfo userVerify(String applyGroupId, String applyState, String note) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        ApplyGroup applyGroup = applyGroupDao.selectByPrimaryKey(applyGroupId);
        if (applyGroup == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此申请记录！");
        }
        Date operateDate = new Date();
        Group group = groupDao.selectByPrimaryKey(applyGroup.getGroupId());
        if (group == null) {
            applyGroup.setApplyState(ApplyStateEnum.REFUSE.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.REFUSE.getState(), chatUser.getUserId());
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此群组记录！");
        }
        // 判断当前用户是否已经加入群组
        if (groupMemberDao.checkUserIsInGroup(applyGroup.getGroupId(), chatUser.getUserId()) > 0) {
            // 既然已经存在用户组中，那把当前的审核状态直接修改为审核通过
            applyGroup.setApplyState(ApplyStateEnum.PASS.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.PASS.getState(), chatUser.getUserId());
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "当前成员已经是该群的成员，无需再次审核！");
        }
        // 若当前审核为审核通过
        if (applyState.equals(ApplyStateEnum.PASS.getState())) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(applyGroup.getGroupId());
            groupMember.setBelowUserId(applyGroup.getApplyUserId());
            groupMember.setJoinDate(operateDate);
            groupMember.setAvatar(chatUser.getAvatar());
            groupMember.setNickName(chatUser.getNickName());
            groupMember.setUserId(chatUser.getUserId());
            groupMemberDao.insertSelective(groupMember);
            applyGroup.setApplyState(ApplyStateEnum.PASS.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.PASS.getState(), chatUser.getUserId());
        } else {
            applyGroup.setApplyState(ApplyStateEnum.REFUSE.getState());
            applyGroup.setVerifyDate(operateDate);
            applyGroupDao.updateByPrimaryKeySelective(applyGroup);
        }
        // 推送申请审核结果给到邀请他的群主
        Channel channel = ChatLoginData.getLoginChannel(applyGroup.getApplyUserId());
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("groupId", applyGroup.getApplyGroupId());
            data.put("verifyDate", operateDate);
            data.put("note", note);
            data.put("applyState", applyState);
            data.put("verifyUserId", chatUser.getUserId());
            data.put("verifyUserNickName", chatUser.getNickName());
            data.put("avatar", chatUser.getAvatar());
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.VERIFYFRIEND.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "审核完成！");
    }

    /**
     * 功能描述： 群主审核入群申请
     *
     * @param applyGroupId 入群申请流水ID
     * @param applyState   审核状态【0：拒绝；9：通过】
     * @param note         审核理由
     * @return 返回审核结果
     */
    public ReturnInfo groupVerify(String applyGroupId, String applyState, String note) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        ApplyGroup applyGroup = applyGroupDao.selectByPrimaryKey(applyGroupId);
        if (applyGroup == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此申请记录！");
        }
        Date operateDate = new Date();
        Group group = groupDao.selectByPrimaryKey(applyGroup.getGroupId());
        if (group == null) {
            applyGroup.setApplyState(ApplyStateEnum.REFUSE.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.REFUSE.getState(), chatUser.getUserId());
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此群组记录！");
        }
        ChatUser applyChatUser = chatUserDao.selectByPrimaryKey(applyGroup.getApplyUserId());
        if (applyChatUser == null) {
            // 当前申请的用户已经注销不存在了
            applyGroup.setApplyState(ApplyStateEnum.REFUSE.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.REFUSE.getState(), chatUser.getUserId());
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "申请用户已注销！");
        }
        // 判断当前用户是否已经加入群组
        if (groupMemberDao.checkUserIsInGroup(applyGroup.getGroupId(), applyGroup.getApplyUserId()) > 0) {
            // 既然已经存在用户组中，那把当前的审核状态直接修改为审核通过
            applyGroup.setApplyState(ApplyStateEnum.PASS.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.PASS.getState(), chatUser.getUserId());
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "当前成员已经是该群的成员，无需再次审核！");
        }
        // 若当前审核为审核通过
        if (applyState.equals(ApplyStateEnum.PASS.getState())) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(applyGroup.getGroupId());
            groupMember.setBelowUserId(applyGroup.getTargetUserId());
            groupMember.setJoinDate(operateDate);
            groupMember.setAvatar(applyChatUser.getAvatar());
            groupMember.setNickName(applyChatUser.getNickName());
            groupMember.setUserId(applyChatUser.getUserId());
            groupMemberDao.insertSelective(groupMember);
            applyGroup.setApplyState(ApplyStateEnum.PASS.getState());
            applyGroup.setVerifyDate(operateDate);
            updateApplyGroupApplyState(applyGroup, ApplyStateEnum.PASS.getState(), chatUser.getUserId());
        } else {
            applyGroup.setApplyState(ApplyStateEnum.REFUSE.getState());
            applyGroup.setVerifyDate(operateDate);
            applyGroupDao.updateByPrimaryKeySelective(applyGroup);
        }
        // 推送申请审核结果给到申请人
        Channel channel = ChatLoginData.getLoginChannel(applyChatUser.getUserId());
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("groupName", group.getGroupName());
            data.put("verifyDate", operateDate);
            data.put("note", note);
            data.put("applyState", applyState);
            data.put("groupId", applyGroup.getGroupId());
            data.put("applyGroupId", applyGroupId);
            data.put("avatar", chatUser.getAvatar());
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.GROUPVERIFY.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "审核完成！");
    }

    /**
     * 功能描述：更新入群申请和群组邀请的记录的状态
     *
     * @param applyGroup 申请信息
     * @param userId     申请人流水ID
     * @param applyState 需要更新的状态
     */
    protected void updateApplyGroupApplyState(ApplyGroup applyGroup, String userId, String applyState) {
        applyGroupDao.updateByPrimaryKeySelective(applyGroup);
        // 同时更新邀请入群申请的数据为审核通过
        applyGroupDao.updateApplyGroupApplyState(applyState, applyGroup.getVerifyDate(), userId, applyGroup.getApplyUserId());
    }

    /**
     * 功能描述： 申请入群的note
     *
     * @param groupId 申请进入的群
     * @param note    伸进入群的消息
     * @return 返回申请入群结果
     */
    public ReturnInfo applyGroup(String groupId, String note) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        // 判断当前用户是否已经加入群组
        if (groupMemberDao.checkUserIsInGroup(groupId, chatUser.getUserId()) > 0) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "您已经是该群的成员，无需再次申请！");
        }
        Group group = groupDao.selectByPrimaryKey(groupId);
        ApplyGroup applyGroup = applyGroupDao.getApplyGroupInfo(chatUser.getUserId(), group.getCrtUserId(), groupId, GroupApplyType.APPLY.getApplyType());
        if (applyGroup != null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "您已经发送过入群申请了，无需再次申请！");
        }
        applyGroup = new ApplyGroup();
        applyGroup.setApplyNickName(chatUser.getNickName());
        applyGroup.setApplyUserId(chatUser.getUserId());
        applyGroup.setTargetUserId(group.getCrtUserId());
        applyGroup.setApplyDate(new Date());
        applyGroup.setGroupId(groupId);
        applyGroup.setNote(note);
        applyGroup.setApplyState(ApplyStateEnum.AUDIT.getState());
        applyGroup.setApplyType(GroupApplyType.APPLY.getApplyType());
        applyGroupDao.insertSelective(applyGroup);
        // 推送申请入群的消息给到群组
        Channel channel = ChatLoginData.getLoginChannel(group.getCrtUserId());
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("applyGroupId", applyGroup.getApplyGroupId());
            data.put("applyUserId", applyGroup.getApplyUserId());
            data.put("applyNickName", applyGroup.getApplyNickName());
            data.put("applyDate", new Date());
            data.put("note", note);
            data.put("groupId", groupId);
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.GROUPAPPLY.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "入群申请发送成功！");
    }

    /**
     * 功能描述： 邀请入群
     *
     * @param groupId 群流水ID
     * @param userId  邀请人流水ID
     * @param note    邀请备注
     * @return 返回邀请结果
     */
    public ReturnInfo invitationGroup(String groupId, String userId, String note) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        Group group = groupDao.selectByPrimaryKey(groupId);
        if (group == null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "查无此群！");
        }
        if (!group.getCrtUserId().equals(chatUser.getUserId())) {
            return new ReturnInfo(SystemEnum.AUTH_FAIL.getKey(), "非法访问！");
        }
        // 判断当前用户是否已经加入群组
        if (groupMemberDao.checkUserIsInGroup(groupId, userId) > 0) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "当前用户已经在群众，无需再次邀请！");
        }
        ApplyGroup applyGroup = applyGroupDao.getApplyGroupInfo(userId, chatUser.getUserId(), groupId, GroupApplyType.INVITATION.getApplyType());
        if (applyGroup != null) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "您已经发送过邀请，无需再次邀请！");
        }
        applyGroup = new ApplyGroup();
        applyGroup.setApplyNickName(chatUser.getNickName());
        applyGroup.setApplyUserId(chatUser.getUserId());
        applyGroup.setTargetUserId(userId);
        applyGroup.setApplyDate(new Date());
        applyGroup.setGroupId(groupId);
        applyGroup.setNote(note);
        applyGroup.setApplyState(ApplyStateEnum.AUDIT.getState());
        applyGroup.setApplyType(GroupApplyType.INVITATION.getApplyType());
        applyGroupDao.insertSelective(applyGroup);
        // 推送邀请入群的申请
        Channel channel = ChatLoginData.getLoginChannel(userId);
        if (channel != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("applyGroupId", applyGroup.getApplyGroupId());
            data.put("applyUserId", applyGroup.getApplyUserId());
            data.put("applyNickName", applyGroup.getApplyNickName());
            data.put("applyDate", new Date());
            data.put("note", note);
            data.put("groupId", groupId);
            CtxWriteUtil.writeAndFlush(channel, new WebsocketMsgVo(MsgTypeEnum.APPLYGROUP.getType(), data));
        }
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "邀请入群成功！");
    }

    /**
     * 功能描述： 实现删除群组
     *
     * @param groupId 群组流水ID
     * @return 返回删除结果
     */
    public ReturnInfo deleteGroup(String groupId) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        // 删除群组
        groupDao.deleteByPrimaryKey(groupId);
        // 实现群组的迁移
        groupDao.transferGroup(chatUser.getDefaultGroupId(), groupId, chatUser.getUserId());
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "删除群组成功！");
    }

    /**
     * 功能描述： 更新群组信息
     *
     * @param groupId   群组流水ID
     * @param groupName 群组名称
     * @param groupImg  群组图标
     * @return 返回更新结果
     */
    public ReturnInfo updateGroup(String groupId, String groupName, String groupImg) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        groupDao.updateGroup(groupId, groupName, groupImg, chatUser.getUserId());
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "更新群组成功！");
    }

    /**
     * 功能描述： 获取群组信息
     *
     * @param groupId 群组流水ID
     * @return 返回查询结果
     */
    public ReturnInfo getGroup(String groupId) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        Group group = groupDao.selectByPrimaryKey(groupId);
        if (chatUser.getUserId().equals(group.getCrtUserId())) {
            return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "获取群组成功！", group);
        }
        return new ReturnInfo(SystemEnum.FAIL.getKey(), "非法访问！");
    }

    /**
     * 功能描述： 增加群组
     *
     * @param groupName 群组名称
     * @param groupImg  群组图标
     * @return 返回增加结果
     */
    public ReturnInfo addGroup(String groupName, String groupImg) {
        ChatUser chatUser = userLoginAuthService.getLoginChatUser();
        if (chatUser == null) {
            return new ReturnInfo(SystemEnum.NOT_LOGIN.getKey(), "当前用户未登录或者登录过期！");
        }
        if (groupDao.checkGroupName(groupName, chatUser.getUserId()) > 1) {
            return new ReturnInfo(SystemEnum.FAIL.getKey(), "群组名称已经存在，请重新命名！");
        }
        Group group = new Group();
        group.setCrtUserId(chatUser.getUserId());
        group.setGroupName(groupName);
        group.setCrtDate(new Date());
        group.setGroupImg(groupImg);
        groupDao.insertSelective(group);
        return new ReturnInfo(SystemEnum.SUCCESS.getKey(), "新增群组成功！", group);
    }

}
