# 简介

---

这个是我学习Java和MySqL后，做的一个**非常非常非常**简单的小项目，在注册、登陆、发帖和、查看帖子等操作中均与数据库进行了一定操作，我知道项目还不完美，甚至漏洞百出，十分不完善，但我本身也只是写来玩玩的。



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
```

3. **系统配置表**

```mysql
create table systemconfig
(
    superUser         varchar(20) null comment '超级用户名',
    superUserPassWord varchar(20) null comment '超级用户密码',
    codeNumber        int         null comment '验证码位数'
) comment '系统配置表' ;
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



