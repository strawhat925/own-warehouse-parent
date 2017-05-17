
### Vue js
[官方文档](https://cn.vuejs.org/v2/guide/forms.html#checkbox)<br />

**项目说明** 
- own-warehouse-parent是一个轻量级权限管理系统，其核心设计目标是开发迅速、学习简单、轻量级、易扩展


**具有如下特点** 
- 轻量级的权限系统，只涉及Spring、Shiro、Mybatis后端框架，降低学习使用成本
- 友好的代码结构及注释，便于阅读及二次开发
- 支持HTML、JSP、Velocity、Freemarker等视图，零技术门槛
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 页面交互使用Vue2.x，极大的提高了开发效率
- 完善的代码生成机制，可在线生成entity、xml、dao、service、html、js、sql代码，减少70%以上的开发任务
- 引入quartz定时任务，可动态完成任务的添加、修改、删除、暂停、恢复及日志查看等功能
- 引入API模板，根据token作为登录令牌，极大的方便了APP接口开发
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入云存储服务，已支持：七牛云、阿里云、腾讯云等
- 引入路由机制，刷新页面会停留在当前页


**项目结构** 
```
own-warehouse-parent
├─doc  项目SQL语句
├─own-warehouse-common 公共模块
│  ├─annotation 公共注解
│  ├─cloud 云存储服务
│  ├─validator 后台校验
│  ├─entity 实体对象
│  ├─exception 通用异常
│  ├─freemarker 模块标签
│  ├─utils 通用工具类
│  ├─xss 
│  └─dao 通用Mapper
│ 
├─own-warehouse-api API模块（接口开发）
│ 
├─own-warehouse-generator 代码生成器模块
│  ├─template 代码生成器模板（可增加或修改相应模板）
│  └─generator.properties 配置文件（配置包名、类型转换等）
│ 
├─own-warehouse-schedule 定时任务模块
│
├─own-warehouse-boss 后台管理系统权限模块
│  ├─statics 第三方库、插件等静态资源、js 系统业务js代码
│  ├─templates freemarker模块
│       ├─sys
│           ├─index.html AdminLTE主题风格（默认主题）
│           └─index1.html Layui主题风格
```


**Layui主题风格：**
![输入图片说明](/doc/2f6a43b9081e421ab8aa596155cd0ffc "在这里输入图片标题")

**AdminLTE主题风格：**
![输入图片说明](/doc/44907148dd254064922a80cfddcc9b53 "在这里输入图片标题")


 **技术选型：** 
- 核心框架：Spring Boot 1.5.2.RELEASE
- 安全框架：Apache Shiro 1.2.5
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.2
- 数据库连接池：Druid 1.0
- 页面交互：Vue2.x


 **软件需求** 
- JDK1.7+
- MySQL5.5+
- Tomcat7.0+
- Maven3.0+



 **本地部署**
- 通过git下载源码
- 创建数据库own_house，数据库编码为UTF-8
- 执行doc/own_house.sql文件，初始化数据【按需导入表结构及数据】
- 修改own-warehouse-boss下的application.properties文件，更新MySQL账号和密码
- IDEA直接运行Application main即可
- 项目访问路径：http://localhost:8080