/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/29 17:11:35                           */
/*==============================================================*/


drop table if exists c_apply_friend;

drop table if exists c_chat_message;

drop table if exists c_chat_user;

drop table if exists c_friend_group;

drop table if exists c_friend_list;

drop table if exists c_group;

drop table if exists c_group_member;

drop table if exists c_receive_message;

/*==============================================================*/
/* Table: c_apply_friend                                        */
/*==============================================================*/
create table c_apply_friend
(
   applyFriendId        varchar(32) not null comment '流水ID',
   applyUserId          varchar(32) comment '申请人流水ID',
   applyNickName        varchar(100) comment '申请人名称',
   targetUserId         varchar(32) comment '接收人ID',
   note                 varchar(100) comment '申请信息',
   applyDate            date comment '申请时间',
   applyState           char(1) comment '申请状态【0：拒绝；1：待通过；2：过期；9：通过】',
   verifyDate           date comment '审核时间',
   applyType            char(1) comment '申请类型【1：好友申请；2：申请入群；】',
   primary key (applyFriendId)
);

/*==============================================================*/
/* Table: c_chat_message                                        */
/*==============================================================*/
create table c_chat_message
(
   chatMessageId        varchar(32) not null comment '流水ID',
   messageContent       varchar(1000) comment '消息内容',
   crtUserId            varchar(32) comment '创建人流水ID',
   crtUserName          varchar(200) comment '创建人名称',
   crtDate              date comment '创建时间',
   primary key (chatMessageId)
);

/*==============================================================*/
/* Table: c_chat_user                                           */
/*==============================================================*/
create table c_chat_user
(
   userId               varchar(32) not null comment '用户流水ID',
   nickName             varchar(200) comment '用户昵称',
   userName             varchar(100) comment '用户账号',
   userState            char(1) comment '用户状态【0：注销；1：冻结；9：正常】',
   crtDate              date comment '创建时间',
   syncUserId           varchar(32) comment '同步系统流水ID',
   userNo               varchar(20) comment '用户编号',
   primary key (userId)
);

/*==============================================================*/
/* Table: c_friend_group                                        */
/*==============================================================*/
create table c_friend_group
(
   friendGroupId        varchar(32) not null comment '分组流水ID',
   crtUserId            varchar(32) comment '分组归属用户ID',
   friendGroupName      varchar(100) comment '分组名称',
   friendGroupOrder     int comment '分组排序',
   allowDeletion        char(1) comment '是否允许删除【0：允许删除；9：不允许删除】',
   primary key (friendGroupId)
);

/*==============================================================*/
/* Table: c_friend_list                                         */
/*==============================================================*/
create table c_friend_list
(
   friendListId         varchar(32) not null comment '好友列表流水ID',
   userId               varchar(32) comment '好友ID',
   belowUserId          varchar(32) comment '所属用户ID',
   friendState          char(1) comment '好友状态【0：已下线；9：已上线】',
   nickName             varchar(200) comment '用户昵称',
   remarks              varchar(200) comment '备注',
   friendGroupId        varchar(32) comment '分组所属ID',
   primary key (friendListId)
);

/*==============================================================*/
/* Table: c_group                                               */
/*==============================================================*/
create table c_group
(
   groupId              varchar(32) not null comment '群组流水ID',
   groupName            varchar(200) comment '群组名称',
   groupImg             varchar(200) comment '群组图标地址',
   crtDate              date comment '创建时间',
   crtUserId            varchar(32) comment '创建人流水ID',
   primary key (groupId)
);

/*==============================================================*/
/* Table: c_group_member                                        */
/*==============================================================*/
create table c_group_member
(
   groupMemberId        varchar(32) not null comment '流水ID',
   groupId              varchar(32) comment '群流水ID',
   userId               varchar(32) comment '成员流水ID',
   joinDate             date comment '加入时间',
   primary key (groupMemberId)
);

/*==============================================================*/
/* Table: c_receive_message                                     */
/*==============================================================*/
create table c_receive_message
(
   receiveMessageId     varchar(32) not null comment '流水ID',
   chatMessageId        varchar(32) comment '消息流水ID',
   sendUserId           varchar(32) comment '发送人流水ID',
   receiveUserId        varchar(32) comment '接收人流水ID',
   receiveDate          date comment '接收时间',
   readDate             date comment '查看时间',
   msgState             char(1) comment '消息状态【0：删除；1：待阅；9：已阅】',
   groupId              varchar(32) comment '所属群组ID',
   primary key (receiveMessageId)
);

