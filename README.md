# 简介

---

这个是我学习JavaSE和MySqL后，做的一个**非常非常非常**简单的小项目，在注册、登陆、发帖和、查看帖子等操作中均与数据库进行了一定操作，我知道项目还不完美，甚至漏洞百出，十分不完善，但我本身也只是写来玩玩的。(反正代码能跑就行)



# 基本功能

---

- 登录
- 注册
- 修改密码
- 修改个人信息
- 发帖
- 查看贴子
- 验证码验证
- root用户管理
  - 查看帖子
  - 删除帖子
  - 新增帖子
  - 修改帖子内容
  - 查看用户
  - 删除用户
  - 新增用户
  - 修改用户(用户名和密码)



# 还未完善

---

- 部分逻辑有问题
- 代码不够简洁
- 还有进一步优化的空间
- 以上全是套话，哈哈哈







# 数据库方面

---

1. **存放帖子的表**

```mysql
create table data
(
    id    int auto_increment comment 'ID号' primary key,
    title       varchar(30)  not null comment '标题',
    publisher   varchar(20)  null comment '发布者',
    publisherId int          null comment '发布者id号',
    content     varchar(500) null comment '内容',
    publishTime datetime     null comment '发布时间',
    constraint foreign_key_userId
        foreign key (publisherId) references user (id)
) comment '论坛内容表' ;

#测试数据
INSERT INTO forum.data (id, title, publisher, publisherId, content, publishTime) VALUES (1, 'test_Title', 'user', 2, 'test_content', '2022-11-01 15:46:23');

```

2. **用户信息表**

```mysql
create table data
(
    id    int auto_increment comment 'ID号' primary key,
    title       varchar(30)  not null comment '标题',
    publisher   varchar(20)  null comment '发布者',
    publisherId int          null comment '发布者id号',
    content     varchar(500) null comment '内容',
    publishTime datetime     null comment '发布时间',
    constraint foreign_key_userId
        foreign key (publisherId) references user (id)
) comment '论坛内容表' ;

#测试数据
INSERT INTO forum.user (id, userName, passWord, gender, age, address) VALUES (1, 'root', 'root', null, null, null);
INSERT INTO forum.user (id, userName, passWord, gender, age, address) VALUES (2, 'user', 'user', null, null, null);
```

3. **系统配置表**

```mysql
create table systemconfig
(
    superUser         varchar(20) null comment '超级用户名',
    superUserPassWord varchar(20) null comment '超级用户密码',
    codeNumber        int         null comment '验证码位数'
) comment '系统配置表' ;

#默认数据
INSERT INTO forum.systemconfig (superUser, superUserPassWord, codeNumber) VALUES ('root', 'root', 4);
```



# Java方面

---

```java
//主机地址
public static final String  HOST = "localhost";
//用户名
public static final String  USER = "root";
//密码
public static final String  PASSWORD = "admin";
//使用的数据库
public static final String  DATABASESNAME = "forum";
```



