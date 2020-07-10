CREATE TABLE `user`(
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(32) DEFAULT NULL COMMENT '姓名',
    `head_img_path` varchar(100) DEFAULT NULL COMMENT'头像图片路径',
    `account` varchar(32) DEFAULT NULL COMMENT'账号',
    `passwd` varchar(64) DEFAULT NULL COMMENT'密码',
    `identity_type` smallint(6) DEFAULT NULL COMMENT'身份:1.核心成员;2.外围成员',
    `cellphone` varchar(15) DEFAULT NULL COMMENT'手机账号',
    `email` varchar(32) DEFAULT NULL COMMENT'邮箱',
    `verify_code` varchar(16) DEFAULT NULL COMMENT '手机验证码',
    PRIMARY KEY(`user_id`),
    UNIQUE KEY `id_UNIQUE`(`user_id`)
);

CREATE TABLE `sys_role`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(32) COMMENT '角色名',
    `enabled` INT COMMENT '有效标记',
    `create_by` bigint(20) COMMENT '创建人',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    primary key(`id`)
);

CREATE TABLE `sys_privilege`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    `privilege_name` varchar(32) COMMENT'权限名',
    `privilege_url` varchar(32) COMMENT'权限url',
    primary key(`id`)
);

CREATE TABLE `sys_user_role`(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID'
);

CREATE TABLE `sys_role_privilege`(
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `privilege_id` bigint(20) NOT NULL COMMENT '权限ID'
);

CREATE TABLE `blog`(
    `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'博文id',
    `user_id` bigint(20) NOT NULL COMMENT '发表用户id',
    `user_avatar` varchar(63) COMMENT '发表的用户头像',
    `blog_title` varchar(255) COMMENT '博文标题',
    `blog_content` longtext COMMENT '博文内容',
    `blog_views` bigint(20) COMMENT '浏览量',
    `blog_comment_account` bigint(20) DEFAULT'0' COMMENT '评论总数',
    `blog_like_account` bigint(20) DEFAULT'0' COMMENT '点赞数',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT'0' COMMENT'是否删除标记',
    PRIMARY KEY(`blog_id`)
);
CREATE TABLE `comments`(
    `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'评论id',
    `blog_id` bigint(20) NOT NULL COMMENT '博文id',
    `user_id` bigint(20) NOT NULL COMMENT '评论用户id',
    `user_avatar` varchar(64) COMMENT '发表的用户头像',
    `parent_comment_id` bigint(20) COMMENT '父评论',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `comment_content` text COMMENT '评论内容',
    `comment_like_account` bigint(20) COMMENT '点赞数',
    `is_deleted` tinyint(1) DEFAULT'0' COMMENT'是否删除标记',
    `is_read` tinyint(1) DEFAULT '0' COMMENT'是否已读标记',
    PRIMARY KEY(`comment_id`)
);

CREATE TABLE `comment_like`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'主键id',
    `comment_id` bigint(20) NOT NULL COMMENT '评论id',
    `create_by` bigint(20) NOT NULL COMMENT '原评论用户id',
    `like_user_id` bigint(20) NOT NULL COMMENT '点赞用户id',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT'0' COMMENT'是否删除标记',
    `is_read` tinyint(1) DEFAULT'0' COMMENT'是否阅读标记',
    PRIMARY KEY(`id`)
);

CREATE TABLE `blog_like`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'主键id',
    `blog_id` bigint(20) NOT NULL COMMENT '博客id',
    `create_by` bigint(20) NOT NULL COMMENT '博文用户id',
    `like_user_id` bigint(20) NOT NULL COMMENT '点赞用户id',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `is_deleted` tinyint(1) DEFAULT'0' COMMENT'是否删除标记',
    PRIMARY KEY(`id`)
);

CREATE TABLE `blog_file`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT'主键id',
    `blog_id` bigint(20) NOT NULL COMMENT'博客id',
    `file_type` tinyint(2) DEFAULT '0' COMMENT'文件类型，0为图片,1为文件',
    `create_time` datetime COMMENT '创建时间',
    `update_time` datetime COMMENT '更新时间',
    `file_path` varchar(100) DEFAULT NULL COMMENT'文件保存路径',
    `is_deleted` tinyint(1) DEFAULT'0' COMMENT'是否删除标记',
    PRIMARY KEY(`id`)
);


