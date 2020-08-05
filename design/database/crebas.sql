/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/8/5 19:29:10                            */
/*==============================================================*/


drop table if exists c_apply_friend;

drop table if exists c_apply_group;

drop table if exists c_chat_message;

drop table if exists c_chat_user;

drop table if exists c_friend;

drop table if exists c_friend_group;

drop table if exists c_group;

drop table if exists c_group_member;

drop table if exists c_receive_message;

/*==============================================================*/
/* Table: c_apply_friend                                        */
/*==============================================================*/
create table c_apply_friend
(
   applyFriendId        varchar(32) not null comment '��ˮID',
   applyUserId          varchar(32) comment '��������ˮID',
   applyNickName        varchar(100) comment '����������',
   targetUserId         varchar(32) comment '������ID',
   note                 varchar(100) comment '������Ϣ',
   applyDate            date comment '����ʱ��',
   applyState           char(1) comment '����״̬��0���ܾ���1����ͨ����2�����ڣ�9��ͨ����',
   verifyDate           date comment '���ʱ��',
   applyType            char(1) comment '�������͡�1���������룻2��������Ⱥ����',
   friendGroupId        varchar(32) comment '������ˮID',
   remark               varchar(100) comment '��ע',
   primary key (applyFriendId)
);

/*==============================================================*/
/* Table: c_apply_group                                         */
/*==============================================================*/
create table c_apply_group
(
   applyGroupId         varchar(32) not null comment '��ˮID',
   applyUserId          varchar(32) comment '��������ˮID',
   applyNickName        varchar(100) comment '����������',
   targetUserId         varchar(32) comment '������ID',
   note                 varchar(100) comment '������Ϣ',
   applyDate            date comment '����ʱ��',
   applyState           char(1) comment '����״̬��0���ܾ���1����ͨ����2�����ڣ�9��ͨ����',
   verifyDate           date comment '���ʱ��',
   applyType            char(1) comment '�������͡�1��������Ⱥ��2��������Ⱥ����',
   groupId              varchar(32) comment 'Ⱥ��ˮID',
   primary key (applyGroupId)
);

/*==============================================================*/
/* Table: c_chat_message                                        */
/*==============================================================*/
create table c_chat_message
(
   chatMessageId        varchar(32) not null comment '��ˮID',
   messageContent       varchar(1000) comment '��Ϣ����',
   crtUserId            varchar(32) comment '��������ˮID',
   crtUserName          varchar(200) comment '����������',
   crtDate              date comment '����ʱ��',
   messageType          char(1) comment '��Ϣ���͡�1��˫�����죻2��Ⱥ�����졿',
   primary key (chatMessageId)
);

/*==============================================================*/
/* Table: c_chat_user                                           */
/*==============================================================*/
create table c_chat_user
(
   userId               varchar(32) not null comment '�û���ˮID',
   nickName             varchar(200) comment '�û��ǳ�',
   userName             varchar(100) comment '�û��˺�',
   userState            char(1) comment '�û�״̬��0��ע����1�����᣻9��������',
   crtDate              date comment '����ʱ��',
   syncUserId           varchar(32) comment 'ͬ��ϵͳ��ˮID',
   userNo               varchar(20) comment '�û����',
   avatar               varchar(200) comment '�û�ͷ���ַ',
   primary key (userId)
);

/*==============================================================*/
/* Table: c_friend                                              */
/*==============================================================*/
create table c_friend
(
   friendId             varchar(32) not null comment '�����б���ˮID',
   userId               varchar(32) comment '����ID',
   belowUserId          varchar(32) comment '�����û�ID',
   friendState          char(1) comment '����״̬��0�������ߣ�9�������ߡ�',
   nickName             varchar(200) comment '�û��ǳ�',
   remarks              varchar(200) comment '��ע',
   friendGroupId        varchar(32) comment '��������ID',
   avatar               varchar(200) comment '�û�ͷ���ַ',
   primary key (friendId)
);

/*==============================================================*/
/* Table: c_friend_group                                        */
/*==============================================================*/
create table c_friend_group
(
   friendGroupId        varchar(32) not null comment '������ˮID',
   crtUserId            varchar(32) comment '��������û�ID',
   friendGroupName      varchar(100) comment '��������',
   friendGroupOrder     int comment '��������',
   allowDeletion        char(1) comment '�Ƿ�����ɾ����0������ɾ����9��������ɾ����',
   primary key (friendGroupId)
);

/*==============================================================*/
/* Table: c_group                                               */
/*==============================================================*/
create table c_group
(
   groupId              varchar(32) not null comment 'Ⱥ����ˮID',
   groupName            varchar(200) comment 'Ⱥ������',
   groupImg             varchar(200) comment 'Ⱥ��ͼ���ַ',
   crtDate              date comment '����ʱ��',
   crtUserId            varchar(32) comment '��������ˮID',
   primary key (groupId)
);

/*==============================================================*/
/* Table: c_group_member                                        */
/*==============================================================*/
create table c_group_member
(
   groupMemberId        varchar(32) not null comment '��ˮID',
   groupId              varchar(32) comment 'Ⱥ��ˮID',
   userId               varchar(32) comment '��Ա��ˮID',
   joinDate             date comment '����ʱ��',
   nickName             varchar(200) comment '�û��ǳ�',
   remarks              varchar(200) comment '��ע',
   avatar               varchar(200) comment '�û�ͷ���ַ',
   primary key (groupMemberId)
);

/*==============================================================*/
/* Table: c_receive_message                                     */
/*==============================================================*/
create table c_receive_message
(
   receiveMessageId     varchar(32) not null comment '��ˮID',
   chatMessageId        varchar(32) comment '��Ϣ��ˮID',
   sendUserId           varchar(32) comment '��������ˮID',
   receiveUserId        varchar(32) comment '��������ˮID',
   receiveDate          date comment '����ʱ��',
   readDate             date comment '�鿴ʱ��',
   msgState             char(1) comment '��Ϣ״̬��0��ɾ����1�����ģ�9�����ġ�',
   groupId              varchar(32) comment '����Ⱥ��ID',
   primary key (receiveMessageId)
);

