DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像图片路径',
  `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个人简介',
  `account` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号,账号唯一',
  `passwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` smallint(6) NULL DEFAULT NULL COMMENT '0:女 1：男 2：保密',
  `cellphone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `verify_code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机验证码',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `account_UNIQUE`(`account`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_user` VALUES (1, '耀眼的风', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '小城里岁月流过去', '18302179970', '8E5EE73EDA2EB08D135E380163929A0A', NULL, '18302179970', '767172685@qq.com', NULL);
INSERT INTO `sys_user` VALUES (2, 'Chen Sir', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '18278366369', '53929F0559E271001AFA4F1A59E19EB7', 1, '18302179970', '474762414@qq.com', '0');
INSERT INTO `sys_user` VALUES (3, '林深不鹿', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '13615052122', 'A6929E49A15A9F762463335052AD473A', NULL, '13615052122', '1171389696@qq.com', NULL);
INSERT INTO `sys_user` VALUES (4, '起床', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '13347558748', 'D8FBA08C2A03911D6C6F295A748EC694', NULL, '13347558748', '798208965qq.com', NULL);
INSERT INTO `sys_user` VALUES (5, 'Control', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '18677560239', '7CAF92AC1970223054D53EB5D0763694', NULL, '18677560239', '635752752qq.com', NULL);
INSERT INTO `sys_user` VALUES (6, 'Zm', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '18378794476', '2D5A8FCC996E9BD4E859EAB6AB39D06E', NULL, '18378794476', '839496982qq.com', NULL);
INSERT INTO `sys_user` VALUES (7, 'Frequently', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '15778110705', 'FB5B2EC3552A49999959B9B7A2B5D640', NULL, '15778110705', '543221616qq.com', NULL);
INSERT INTO `sys_user` VALUES (8, '多梦时节', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '18856307726', '4A37BDB4879395C08C8ABD0E33DADC11', NULL, '18856307726', '1337622954qq.com', NULL);
INSERT INTO `sys_user` VALUES (9, '.', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '13377251669', '1452CBA7E07353519DCC6C6A57D487DD', NULL, '13377251669', '459327354qq.com', NULL);
INSERT INTO `sys_user` VALUES (10, '朗读者', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, '18577524040', '9BB598808FEEC5CDB6A89A55C705FF31', NULL, '18577524040', '279451583qq.com', NULL);



DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_role` VALUES (1, 'admin', '系统管理员', '2020-09-27 14:09:37', '2020-09-27 14:09:39');
INSERT INTO `sys_role` VALUES (2, 'editor', '博客管理员', '2020-09-27 14:10:14', '2020-09-27 14:10:16');
INSERT INTO `sys_role` VALUES (3, 'visitor', '游客', '2021-01-06 20:28:47', '2021-01-06 20:28:49');
INSERT INTO `sys_role` VALUES (4, 'logAdmin', '日志管理员', '2021-01-06 20:28:47', '2021-01-06 20:28:49');
INSERT INTO `sys_role` VALUES (5, 'ledgerAdmin', '账单管理员', '2021-01-06 20:28:47', '2021-01-06 20:28:49');


DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege`  (
  `privilege_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `privilege_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `privilege_url` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限url',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`privilege_id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_privilege` VALUES (1, '用户管理权限', NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege` VALUES (2, '博客管理权限', NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege` VALUES (3, '一般权限', NULL, NULL, NULL, NULL);
INSERT INTO `sys_privilege` VALUES (4, '账单管理权限', NULL, NULL, NULL, NULL);


DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT `FK_USERROLE_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `FK_USERROLE_ROLE` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`),
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_user_role` VALUES (1, 1, 1, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (2, 2, 2, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (3, 3, 2, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (4, 4, 2, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (5, 5, 2, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (6, 6, 3, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (7, 7, 3, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (8, 8, 2, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (9, 9, 3, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (10, 10, 3, NULL, NULL);


DROP TABLE IF EXISTS `sys_role_privilege`;
CREATE TABLE `sys_role_privilege`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `privilege_id` bigint(20) NOT NULL COMMENT '权限ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT `FK_ROLEPRIVILEGE_ROLE` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`),
  CONSTRAINT `FK_ROLEPRIVILEGE_PRIVILEGE` FOREIGN KEY (`privilege_id`) REFERENCES `sys_privilege` (`privilege_id`),
  PRIMARY KEY (`id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `sys_role_privilege` VALUES (1, 1, 1, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (2, 1, 2, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (3, 1, 3, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (4, 2, 2, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (5, 2, 3, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (6, 3, 3, NULL, NULL);
INSERT INTO `sys_role_privilege` VALUES (7, 5, 4, NULL, NULL);


DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `blog_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博文id',
  `user_id` bigint(20) NOT NULL COMMENT '发表用户id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'summary',
  `blog_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博文标题',
  `blog_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '博文内容',
  `total_views` bigint(20) NULL DEFAULT NULL COMMENT '浏览总量',
  `total_comments` bigint(20) NULL DEFAULT 0 COMMENT '博客被评论评论总数',
  `total_likes` bigint(20) NULL DEFAULT 0 COMMENT '该博客获得的点赞总数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  PRIMARY KEY (`blog_id`) USING BTREE,
  CONSTRAINT `FK_BLOG_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `blog` VALUES (1, 1, '纯阳之花', '花海', '<h2><span class=\"ql-size-huge\">《花海》</span></h2>\n<p>&nbsp;</p>\n<p>作曲 : 周杰伦</p>\n<p>&nbsp;</p>\n<p>作词 : 古小力du/黄凌嘉</p>\n<p>&nbsp;</p>\n<p>歌手：周杰伦</p>\n<p>&nbsp;</p>\n<p>歌词：</p>\n<p>&nbsp;</p>\n<p>静止了，所有的花开</p>\n<p>&nbsp;</p>\n<p>遥远了，清晰了爱</p>\n<p>&nbsp;</p>\n<p>天郁闷，爱dao却更喜欢</p>\n<p>&nbsp;</p>\n<p>那时候，我不懂</p>\n<p>&nbsp;</p>\n<p>这叫爱，你喜欢</p>\n<p>&nbsp;</p>\n<p>站在那窗台，你好久</p>\n<p>&nbsp;</p>\n<p>都没再来，彩色的</p>\n<p>&nbsp;</p>\n<p>时间染上空白，是你流的泪晕开</p>\n<p>&nbsp;</p>\n<p>不要你离开，距离隔不开</p>\n<p>&nbsp;</p>\n<p>思念变成海，在窗外进不来</p>\n<p>&nbsp;</p>\n<p>原谅说太快，爱成了阻碍</p>\n<p>&nbsp;</p>\n<p>手中的风筝放太快，回不来</p>\n<p>不要你离开，回忆划不开</p>\n<p>&nbsp;</p>\n<p>欠你的宠爱，我在等待重来</p>\n<p>&nbsp;</p>\n<p>天空仍灿烂，它爱着大海</p>\n<p>&nbsp;</p>\n<p>情歌被打败，爱已不存在</p>\n<p>&nbsp;</p>\n<p>你喜欢，站在那窗台</p>\n<p>&nbsp;</p>\n<p>你好久，都没再来</p>\n<p>&nbsp;</p>\n<p>彩色的，时间染上空白</p>\n<p>&nbsp;</p>\n<p>是你流的泪晕开，不要你离开</p>\n<p>&nbsp;</p>\n<p>距离隔不开，思念变成海</p>\n<p>&nbsp;</p>\n<p>在窗外进不来，原谅说太快</p>\n<p>&nbsp;</p>\n<p>爱成了阻碍，手中的风筝放太快</p>\n<p>&nbsp;</p>\n<p>回不来，不要你离开</p>\n<p>&nbsp;</p>\n<p>回忆划不开，欠你的宠爱</p>\n<p>&nbsp;</p>\n<p>我在等待重来，天空仍灿烂</p>\n<p>&nbsp;</p>\n<p>它爱着大海，情歌被打败</p>\n<p>&nbsp;</p>\n<p>爱已不存在</p>', 0, 0, 0, '2020-09-18 14:28:18', '2020-09-18 14:28:21', 0);
INSERT INTO `blog` VALUES (2, 1, '你说你舍不得吃掉这一种感觉', '七里香', '<p>窗外的麻雀，在电线杆上多嘴</p>\n<p>你说这一句，很有夏天的感觉</p>\n<p>手中的铅笔，在纸上来来回回</p>\n<p>我用几行字形容你，是我的谁</p>\n<p>秋刀鱼的滋味，猫跟你都想了解</p>\n<p>初恋的香味，就这样被我们寻回</p>\n<p>那温暖的阳光像刚摘的鲜艳草莓</p>\n<p>你说你舍不得吃掉这一种感觉</p>\n<p>雨下整夜，我的爱溢出就像雨水</p>\n<p>院子落叶，跟我的思念厚厚一叠</p>\n<p>几句是非，也无法将我的热情冷却</p>\n<p>你出现在我诗的每一页</p>\n<p>雨下整夜，我的爱溢出就像雨水</p>\n<p>窗台蝴蝶，像诗里纷飞的美丽章节</p>\n<p>我接着写，把永远爱你写进诗的结尾</p>\n<p>你是我唯一想要的了解</p>\n<p>雨下整夜，我的爱溢出就象雨水</p>\n<p>院子落叶，跟我的思念厚厚一叠</p>\n<p>几句是非，也无法将我的热情冷却</p>\n<p>你出现在我诗的每一页<br />那饱满的稻穗，幸福了这个季节</p>\n<p>而你的脸颊像田里熟透的蕃茄</p>\n<p>你突然对我说七里香的名字很美</p>\n<p>我此刻却只想亲吻你倔强的嘴</p>\n<p>雨下整夜，我的爱溢出就像雨水</p>\n<p>院子落叶，跟我的思念厚厚一叠</p>\n<p>几句是非，也无法将我的热情冷却</p>\n<p>你出现在我诗的每一页</p>\n<p>雨下整夜，我的爱溢出就像雨水</p>\n<p>窗台蝴蝶，像诗里纷飞的美丽章节</p>\n<p>我接着写，把永远爱你写进诗的结尾</p>\n<p>你是我唯一想要的了解</p>', 0, 0, 0, '2020-12-26 13:53:01', '2020-12-26 13:53:01', 0);
INSERT INTO `blog` VALUES (3, 1, '我一路向北离开有你的季节', '一路向北', '<p class=\"wa-musicsong-lyric-line\">后视镜里的世界</p>\n<p class=\"wa-musicsong-lyric-line\">越来越远的道别</p>\n<p class=\"wa-musicsong-lyric-line\">你转身向背 侧脸还是很美</p>\n<p class=\"wa-musicsong-lyric-line\">我用眼光去追 竟听见你的泪</p>\n<p class=\"wa-musicsong-lyric-line\">在车窗外面徘徊 是我错失的机会</p>\n<p class=\"wa-musicsong-lyric-line\">你站的方位 跟我中间隔着泪</p>\n<p class=\"wa-musicsong-lyric-line\">街景一直在后退 你的崩溃在窗外零碎</p>\n<p class=\"wa-musicsong-lyric-line\">我一路向北 离开有你的季节</p>\n<p class=\"wa-musicsong-lyric-line\">你说你好累 已无法再爱上谁</p>\n<p class=\"wa-musicsong-lyric-line\">风在山路吹 过往的画面全都是我不对</p>\n<p class=\"wa-musicsong-lyric-line\">细数惭愧 我伤你几回</p>\n<p class=\"wa-musicsong-lyric-line\">后视镜里的世界</p>\n<p class=\"wa-musicsong-lyric-line\">越来越远的道别</p>\n<p class=\"wa-musicsong-lyric-line\">你转身向背 侧脸还是很美</p>\n<p class=\"wa-musicsong-lyric-line\">我用眼光去追 竟听见你的泪</p>\n<p class=\"wa-musicsong-lyric-line\">在车窗外面徘徊</p>\n<p class=\"wa-musicsong-lyric-line\">是我错失的机会</p>\n<p class=\"wa-musicsong-lyric-line\">你站的方位 跟我中间隔着泪</p>\n<p class=\"wa-musicsong-lyric-line\">街景一直在后退 你的崩溃在窗外零碎</p>\n<p class=\"wa-musicsong-lyric-line\">我一路向北 离开有你的季节</p>\n<p class=\"wa-musicsong-lyric-line\">你说你好累 已无法再爱上谁</p>\n<p class=\"wa-musicsong-lyric-line\">风在山路吹 过往的画面全都是我不对</p>\n<p class=\"wa-musicsong-lyric-line\">细数惭愧 我伤你几回</p>\n<p class=\"wa-musicsong-lyric-line\">我一路向北 离开有你的季节</p>\n<p class=\"wa-musicsong-lyric-line\">方向盘周围 回转着我的后悔</p>\n<p class=\"wa-musicsong-lyric-line\">我加速超越 却甩不掉紧紧跟随的伤悲</p>\n<p class=\"wa-musicsong-lyric-line\">细数惭愧 我伤你几回</p>\n<p class=\"wa-musicsong-lyric-line\">停止狼狈 就让错纯粹</p>', 0, 0, 0, '2020-12-26 13:53:24', '2020-12-26 13:53:24', 0);
INSERT INTO `blog` VALUES (4, 2, '苍天笑纷纷世上潮', '沧海一声笑', '<p>沧海笑滔滔两岸潮</p>\n<p><br />浮沉随浪记今朝</p>\n<p><br />苍天笑纷纷世上滔</p>\n<p><br />谁负谁胜出天知晓</p>\n<p><br />江山笑烟雨遥</p>\n<p><br />涛浪汹尽红尘俗世知多少</p>\n<p><br />清风笑竟惹寂寥</p>\n<p><br />豪情还剩了一襟晚照</p>\n<p><br />苍生笑不再寂寥</p>\n<p><br />豪情仍在痴痴笑笑</p>', 0, 0, 0, '2021-01-06 11:08:45', '2021-01-06 11:08:45', 0);
INSERT INTO `blog` VALUES (5, 2, '大雨颠倒了城市。都会想起那蜻蜓点水般的爱情，想起那擦肩而过的身影和那昙花……', '小情歌', '<p>这是一首简单的小情歌&nbsp; 唱着人们bai心肠的曲折</p>\n<p>我想我很快乐&nbsp; 当有你的温热&nbsp; 脚边的空气转了</p>\n<p>这是一首简单的小情歌&nbsp; 唱着我们心头的白鸽</p>\n<p>我想我很适合&nbsp; 当一个歌颂者&nbsp; 青春在风中飘着</p>\n<p>你知道&nbsp; 就算大雨让这座城市颠倒&nbsp; 我会给你怀抱</p>\n<p>受不了看见你背影来到&nbsp; 写下我度秒如年难捱的离骚</p>\n<p>就算整个世界被寂寞绑票&nbsp; 我也不会奔跑</p>\n<p>逃不了最后谁也都苍老&nbsp; 写下我时间和琴声交错的城堡</p>\n<p>这是一首简单的小情歌&nbsp; 唱着我们心头的白鸽</p>\n<p>我想我很适合&nbsp; 当一个歌颂者&nbsp; 青春在风中飘着</p>\n<p>你知道&nbsp; 就算大雨让这座城市颠倒&nbsp; 我会给你怀抱</p>\n<p>受不了看见你背影来到&nbsp; 写下我度秒如年难捱的离骚</p>\n<p>就算整个世界被寂寞绑票&nbsp; 我也不会奔跑</p>\n<p>逃不了最后谁也都苍老&nbsp; 写下我时间和琴声交错的城堡</p>\n<p>你知道&nbsp; 就算大雨让这座城市颠倒&nbsp; 我会给你怀抱</p>\n<p>受不了看见你背影来到&nbsp; 写下我度秒如年难捱的离骚</p>\n<p>就算整个世界被寂寞绑票&nbsp; 我也不会奔跑</p>\n<p>最后谁也都苍老&nbsp; 写下我时间和琴声交错的城堡</p>', 0, 0, 0, '2021-01-06 11:20:37', '2021-01-06 11:20:37', 0);


DROP TABLE IF EXISTS `blog_file`;
CREATE TABLE `blog_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `file_type` tinyint(4) NULL DEFAULT 0 COMMENT '文件类型，0为图片,1为文件',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `file_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件保存路径',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `FK_BLOGFILE_BLOG` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `blog_of_like`;
CREATE TABLE `blog_of_like`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NOT NULL COMMENT '博客id',
  `user_id` bigint(20) NOT NULL COMMENT '博文的用户id',
  `user_id_of_like` bigint(20) NOT NULL COMMENT '点赞的用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否阅读标记',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `FK_BLOGLIKE_BLOG` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`),
  CONSTRAINT `FK_BLOGLIKE_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `FK_BLOGLIKE_USER_USERIDOFLIKE` FOREIGN KEY (`user_id_of_like`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `blog_id` bigint(20) NOT NULL COMMENT '博文id',
  `user_id` bigint(20) NOT NULL COMMENT '发出该评论的用户id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '指向父评论的id,如果不是对评论的回复,那么该值为0',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `comment_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `total_likes` bigint(20) NULL DEFAULT NULL COMMENT '该评论获得的点赞总数',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否已读标记',
  PRIMARY KEY (`comment_id`) USING BTREE,
  CONSTRAINT `FK_COMMENT_BLOG` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`),
  CONSTRAINT `FK_COMMENT_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `comment` VALUES (1, 1, 1, 0, '2021-01-07 21:35:40', '2021-01-07 21:35:41', '闹得还是不够大！！希望风景线更大！哈哈哈', NULL, 0, 0);
INSERT INTO `comment` VALUES (2, 1, 2, 1, '2021-01-07 21:37:31', '2021-01-07 21:37:32', '川普太令人失望了，居然让支持者回家......陛下先投降了', NULL, 0, 0);
INSERT INTO `comment` VALUES (3, 1, 1, 2, '2021-01-07 21:38:26', '2021-01-07 21:38:27', '看出殡不嫌殡大', NULL, 0, 0);
INSERT INTO `comment` VALUES (4, 1, 1, 3, '2021-01-08 05:09:21', '2021-01-08 05:09:21', '网络上你重拳出击', NULL, 0, 0);


DROP TABLE IF EXISTS `comment_of_like`;
CREATE TABLE `comment_of_like`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `comment_id` bigint(20) NOT NULL COMMENT '评论id',
  `user_id` bigint(20) NOT NULL COMMENT '评论的用户id',
  `user_id_of_like` bigint(20) NOT NULL COMMENT '点赞的用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  `is_read` tinyint(1) NULL DEFAULT 0 COMMENT '是否阅读标记',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `FK_COMMENTLIKE_COMMENT` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`),
  CONSTRAINT `FK_COMMENTLIKE_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `FK_COMMENTLIKE_USER_USERIDOFLIKE` FOREIGN KEY (`user_id_of_like`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '请求创建时间',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '接口描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口url',
  `url_args` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口参数',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `operation_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`log_id`) USING BTREE,
  CONSTRAINT `FK_OPERATIONLOG_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `exception_log`;
CREATE TABLE `exception_log`  (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '请求创建时间',
  `exc_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '接口描述',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口url',
  `url_args` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口参数',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法名',
  `http_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的http类型',
  PRIMARY KEY (`log_id`) USING BTREE,
  CONSTRAINT `FK_EXCEPTIONLOG_USER` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `blog_id` bigint(20) NULL DEFAULT NULL COMMENT '与之相关联的id',
  `tag_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  PRIMARY KEY (`tag_id`) USING BTREE,
  CONSTRAINT `FK_TAG_BLOG` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `ledger`;
CREATE TABLE `ledger`  (
  `ledger_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pre_ledger_id` bigint(20) NOT NULL COMMENT '之前的记录id',
  `user_id` bigint(20) NOT NULL COMMENT '记录所属用户',
  `modified_by` bigint(20) NOT NULL COMMENT '修改人',
  `reason` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '具体原因',
  `total` bigint(20) NULL DEFAULT NULL COMMENT '总数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记',
  PRIMARY KEY (`ledger_id`) USING BTREE,
  CONSTRAINT `FK_LEDGER_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `FK_LEDGER_MODIFIEDBY` FOREIGN KEY (`modified_by`) REFERENCES `sys_user` (`user_id`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;