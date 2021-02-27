# Hi-Dream-Blog
# springboot+vue博客系统

#### **项目前言**

本项目目前还在完善中，功能也在不断优化中，前端页面编写花费时间较后端少，因为之前写了不少博客样式，有些拿来改改就行，[Semantic UI 博客样式](https://github.com/dateolive/mdy-blog) 这个是之前写的一个博客样式，本来想打算用springboot和thymeleaf开发博客的，后来想了想，前后端分离才是王道，所以采取了springboot和vue来开发，vue是我之前学的，所以容易上手。而springboot框架是我这阵子才刚刚起步，在b站学习完了狂神大大的视频后，趁热做的一个练手项目，可能会有很多bug，敬请原谅~~

该博客系统的sql文件以及一些静态文件：[https://github.com/dateolive/Hi-Dream-BlogReSource](https://github.com/dateolive/Hi-Dream-BlogReSource)

博客sql文件导入之后，初始账户密码是： 账户：`Vistortest` 密码：`Vistortest`

[博客小程序](https://github.com/dateolive/halo-dream)

这是我之前开发的一款基于wordpress的博客微信小程序，后面会将本博客系统的接口迁移到这个微信小程序上，提供一个博客微信小程序。



如果你对这个博客系统感兴趣，或者想交pr的，可以联系我，加入到Organizations里。

博客学习交流群：

<img src="https://gitee.com/zhangrenfeng/images/raw/master/img/20210227210248.png" alt="image-20210227210248766" style="zoom:25%;" />

有问题可以进群问哈~

#### **项目预览**

- 前台：[https://blog.datealive.top/](https://blog.datealive.top/)
- 后台：[https://admin.datealive.top/](https://admin.datealive.top/)     游客账户：`Vistortest` 密码：`Vistortest`

#### **项目描述**

1.前端分为两个项目：一个是博客前台页面，一个是博客后台管理页面。

博客前台页面：前台页面采用elementUI框架和自己写的一些css样式实现，目前样式不太协调，敬请谅解。

博客后台页面：后台采取自定义修改GitHub开源后台管理项目[vue-manage-system](vue-manage-system
)

2.后端采用springboot实现，使用shiro和jwt做用户认证和授权，redis做nosql缓存，项目部署在宝塔面板上。 

#### **技术栈**

 **前端**

| 名称       | 技术点                    |
| ---------- | ------------------------- |
| 基础       | vue                       |
| UI框架     | elementUI                 |
| 文本编辑器 | MavonEditor               |
| 后台模板   | vue-manage-system开源模板 |

**后端**

| 名称     | 技术点                                                       |
| -------- | ------------------------------------------------------------ |
| 开发     | Springboot+Mybatis                                           |
| 数据库   | MySQL+Druid                                                  |
| 缓存     | redis                                                        |
| 权限框架 | shiro  ， jwt                                                |
| 其他     | 定时任务，Lombok，log4j ，分页插件：PageHelper，邮件任务..... |

#### **遇到的问题**


**关于阅读次数**

刚开始是每次刷新就去请求数据库，后面实现了redis缓存阅读量，实现定时任务更新文章访问量

**更换数据库编码问题**
将原来的项目的数据库更换成utf8mb4编码，需要将datasource的url中的```useUnicode=true&characterEncoding=utf8```去除

**Safari 浏览器访问不了当前站点**
昨晚，有个网友发来说iPad的Safari 浏览器访问不了当前站点，我仔细排查了下，发现可能是Safari 浏览器访问链接时http自动转换为https，但我这个二级域名还没部署ssl证书，导致访问站点链接时，未开启SSL的站点使用HTTPS会直接访问到已开启SSL的站点，所以就跳转到了我之前的wordpress博客地址，所以现在就在站点上部署了ssl证书，测试了下，发现已经能够正常访问了。

**前端前台页面使用mavonEditor导致tocbot目录插件失效**

后端返回博客详情内容时，使用commonmark将markdown转为html  解决p标签无class导致tocbot无法生成问题。

#### **关于开源**

代码已上传GitHub，欢迎给个star~ ，有能力的朋友，可以请我喝一杯奶茶。

[Hi-Dream-Blog](https://github.com/dateolive/Hi-Dream-Blog)

<img src="https://gitee.com/zhangrenfeng/images/raw/master/img/20210227173958.jpg" alt="zansan" style="zoom:25%;" />

#### **关于Organizations**

现在陆陆续续有人加入了这个博客的Organizations，给这个博客系统提供了很大的帮助，同时也给了很棒的建议，在此十分感谢他们。

这里只展示目前对项目有贡献的朋友。

- [lengyue1024](https://github.com/lengyue1024)



#### **关于我**

由于我目前要开学了，后面可能没很大的精力投入这个博客系统开发了，更新迭代的速度也会变慢。敬请原谅。如果有什么bug或者建议，可以及时联系我。

qq:2448282543



#### **参考项目**

- [MarkerHub](https://github.com/MarkerHub/vueblog)
- [Fblog](https://gitee.com/fengziy/Fblog)
- [NBlog](https://github.com/Naccl/NBlog)

**十分感谢上面这些开源项目，感谢！！**