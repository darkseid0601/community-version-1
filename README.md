# community-version-1：仿牛客网项目
# 开发历程：
## 2022-5-20

### 1、开发社区首页，显示前10个帖子

### 2、开发分页组件，分页显示所有的帖子

## 2022-5-21

新增功能：

### 1、发送邮件

> #### • 邮箱设置
>
> - 启用客户端SMTP服务
>
> #### • **Spring Email**
>
> - 导入 jar 包
>
> - 邮箱参数配置
> - 使用 JavaMailSender 发送邮件
>
> #### • 模板引擎
>
> - 使用 Thymeleaf 发送 HTML 邮件

### 2、开发注册功能

> #### • 访问注册页面
>
> - 点击顶部区域内的链接，打开注册页面。
>
> #### • 提交注册数据
>
> - 通过表单提交数据。
> - 服务端验证账号是否已存在、邮箱是否已注册。
> - 服务端发送激活邮件。
>
> #### • 激活注册账号
>
> - 点击邮件中的链接，访问服务端的激活服务。



## 2022-5-22

新增功能：

### 1、生成验证码

> #### • Kaptcha
>
> - 导入 jar 包
> - 编写 Kaptcha 配置类
> - 生成随机字符、生成图片

### 2、开发登录、退出功能

> #### • 访问登录页面
>
> - 点击顶部区域内的链接，打开登录页面。
>
> #### • 登录
>
>   - 验证账号、密码、验证码。
>   - 成功时，生成登录凭证，发放给客户端。
>   - 失败时，跳转回登录页。
>
> #### • 退出
>
>   - 将登录凭证修改为失效状态。
>   - 跳转至网站首页。

## 2022-5-23

新增功能：

### 1、显示登录信息

> #### • 拦截器示例
>
> - 定义拦截器，实现HandlerInterceptor
>
> - 配置拦截器，为它指定拦截、排除的路径
>
> #### • 拦截器应用
>
>   - 在请求开始时查询登录用户
>   - 在本次请求中持有用户数据
>   - 在模板视图上显示用户数据
>   - 在请求结束时清理用户数据

### 2、账号设置

> #### • 上传文件
>
>    - 请求：必须是POST请求
>    - 表单：enctype=“multipart/form-data”
>    - Spring MVC：通过 MultipartFile 处理上传文件
>
> #### • 开发步骤
>
> - 访问账号设置页面
> - 上传头像
> - 获取头像

## 2022-5-24

新增功能：

### 1、检查登录状态（用于防止未登录用户访问登录用户才能访问的信息）

> #### • 使用拦截器
>
> - 在方法前标注自定义注解
> - 拦截所有请求，只处理带有该注解的方法
>
> #### • 自定义注解
>
> - 常用的元注解：
>
>   @Target、@Retention、@Document、@Inherited
>
> - 如何读取注解：
>
>   Method.getDeclaredAnnotations ()
>
>   Method.getAnnotation (Class<T> annotationClass)

## 2022-5-25

新增功能：

### 1、忘记密码

> - 点击登录页面上的“忘记密码”链接，打开忘记密码页面。
> - 在表单中输入注册的邮箱，点击获取验证码按钮，服务器为该邮箱发送一份验证码。
> - 在表单中填写收到的验证码及新密码，点击重置密码，服务器对密码进行修改。

### 2、修改密码

>    - 在账号设置页面，填写原密码以及新密码，点击保存时将数据提交给服务器。
>    - 服务器检查原密码是否正确，若正确则将密码修改为新密码，并重定向到退出功能，强制用户重新登录。若错误则返回到账号设置页面，给与相应提示。

## 2022-5-26

新增功能：

### 1、敏感词过滤

> #### 前缀树
>
> - 名称：Trie、字典树、查找树
> - 特点：查找效率高，消耗内存大
> - 应用：字符串检索、词频统计、字符串排序等
>
> #### 敏感词过滤器
>
> - 定义前缀树
> - 根据敏感词，初始化前缀树
> - 编写过滤敏感词的方法

### 2、发布帖子（使用AJAX）

> - Asynchronous JavaScript and XML
>
> - 异步的JavaScript与XML，不是一门新技术，只是一个新的术语。
> - 使用AJAX，网页能够将增量更新呈现在页面上，而不需要刷新整个页面。
> - 虽然X代表XML，但目前JSON的使用比XML更加普遍。
> - 采用AJAX请求，实现发布帖子的功能。

### 3、显示帖子详情

> - 显示标题、作者、发布时间、帖子正文等内容

## 2022-5-27

新增功能：

### 1、显示评论

> #### 数据层
>
> - 根据实体查询一页评论数据。
> - 根据实体查询评论的数量。
>
> #### 业务层
>
>   - 处理查询评论的业务。
>   - 处理查询评论数量的业务。
>
> #### 表现层
>
>   - 显示帖子详情数据时，同时显示该帖子所有的评论数据。

### 2、添加评论

> #### 数据层
>
>   - 增加评论数据。
>   - 修改帖子的评论数量。
>
> #### 业务层
>
>   - 处理添加评论的业务：
>     先增加评论、再更新帖子的评论数量。
>
> #### 表现层
>
>   - 处理添加评论数据的请求。
>   - 设置添加评论的表单。

## 2022-5-28

新增功能：

### 1、私信列表

> #### 私信列表
>
>    - 查询当前用户的会话列表，每个会话只显示一条最新的私信。
>
> - 支持分页显示。
>
> #### 私信详情
>
>   - 查询某个会话所包含的私信。
>   - 支持分页显示。

### 2、发送私信

> #### 发送私信
>
> - 采用异步的方式发送私信。
> - 发送成功后刷新私信列表。
>
> #### 设置已读
>
> - 访问私信详情时，将显示的私信设置为已读状态。

## 2022-5-29

新增功能：

### 1、统一处理异常

> #### @ControllerAdvice
>
> - 用于修饰类，表示该类是Controller的全局配置类。
>
> - 在此类中，可以对Controller进行如下三种全局配置：
>
>   异常处理方案、绑定数据方案、绑定参数方案。
>
> #### 异常处理：@ExceptionHandler
>
> - 用于修饰方法，该方法会在Controller出现异常后被调用，用于处理捕获到的异常。

### 2、统一记录日志

> #### AOP的实现
>
> #### • AspectJ
>
> - AspectJ是语言级的实现，它扩展了Java语言，定义了AOP语法。
> - AspectJ在编译期织入代码，它有一个专门的编译器，用来生成遵守Java字节码规范的class文件。
>
> #### • Spring AOP
>
>   - Spring AOP使用纯Java实现，它不需要专门的编译过程，也不需要特殊的类装载器。
>   - Spring AOP在运行时通过代理的方式织入代码，只支持方法类型的连接点。
>   - Spring支持对AspectJ的集成。
>
> #### Spring AOP
>
> #### • JDK动态代理
>
> - Java提供的动态代理技术，可以在运行时创建接口的代理实例。
> - Spring AOP默认采用此种方式，在接口的代理实例中织入代码。
>
> #### • CGLib动态代理
>
>   - 采用底层的字节码技术，在运行时创建子类代理实例。
>   - 当目标对象不存在接口时，Spring AOP会采用此种方式，在子类实例中织入代码。

### 3、删除私信

>   - 点击某条私信的删除按钮时，将其状态设置为删除态

## 2022-5-31

新增功能：

### 1、点赞

> #### • 点赞
>
> - 支持对帖子、评论点赞。
> - 第1次点赞，第2次取消点赞。
    >   • 首页点赞数量
> - 统计帖子的点赞数量。
    >   • 详情页点赞数量
> - 统计点赞数量。
> - 显示点赞状态。

### 2、收到的赞

> #### • 重构点赞功能
>
> - 以用户为key，记录点赞数量
> - increment(key)，decrement(key)
>
> #### • 开发个人主页
>
> - 以用户为key，查询点赞数量

## 2022-6-1

新增功能：

### 1、关注、取消关注

> #### •  需求
>
> - 开发关注、取消关注功能。
> - 统计用户的关注数、粉丝数。
> #### • 关键
>
> - 若A关注了B，则A是B的Follower（粉丝），B是A的Followee（目标）。
> - 关注的目标可以是用户、帖子、题目等，在实现时将这些目标抽象为实体。

### 2、关注列表、粉丝列表

> #### •  业务层
>
> - 查询某个用户关注的人，支持分页。
> - 查询某个用户的粉丝，支持分页。
> #### • 表现层
>
> - 处理“查询关注的人”、“查询粉丝”请求。
> - 编写“查询关注的人”、“查询粉丝”模板。

### 3、优化登录模块

> #### •  使用Redis存储验证码
>
> - 验证码需要频繁的访问与刷新，对性能要求较高。
> - 验证码不需永久保存，通常在很短的时间后就会失效。
> - 分布式部署时，存在Session共享的问题.
> #### • 使用Redis存储登录凭证
>
> - 处理每次请求时，都要查询用户的登录凭证，访问的频率非常高。
> #### • 使用Redis缓存用户信息
>
> - 处理每次请求时，都要根据凭证查询用户信息，访问的频率非常高。

## 2022-6-2

新增功能：

### 1、我的帖子

> - 在个人主页上，显示当前用户曾经发布过的帖子。

### 2、我的回复

> - 在个人主页上，显示当前用户曾经为帖子发布过的评论。

## 2022-6-4

新增功能：

### 1、使用Kafka发送系统通知

> #### •  触发事件
>
> - 评论后，发布通知
> - 点赞后，发布通知
> - 关注后，发布通知
> #### • 处理事件
>
> - 封装事件对象
> - 开发事件的生产者
> - 开发事件的消费者

### 2、显示系统通知

> #### •  通知列表
>
> - 显示评论、点赞、关注三种类型的通知
> #### • 通知详情
>
> - 分页显示某一类主题所包含的通知
> #### • 未读消息
>
> - 在页面头部显示所有的未读消息数量

## 2022-6-11

新增功能：

### 1、开发社区搜索功能(需要注意ES6和ES7的不同)
参考[https://blog.csdn.net/wpw2000/article/details/115704320?spm=1001.2014.3001.5502](https://blog.csdn.net/wpw2000/article/details/115704320?spm=1001.2014.3001.5502)

> #### •  搜索服务
>
> - 将帖子保存至Elasticsearch服务器。
> - 从Elasticsearch服务器删除帖子。
> - 从Elasticsearch服务器搜索帖子。
> #### • 发布事件
>
> - 发布帖子时，将帖子异步的提交到Elasticsearch服务器。
> - 增加评论时，将帖子异步的提交到Elasticsearch服务器。
> - 在消费组件中增加一个方法，消费帖子发布事件。
>
> #### •  显示结果
>
> - 在控制器中处理搜索请求，在HTML上显示搜索结果。

## 2022-6-13

新增功能：

### 1、使用SpringSecurity进行权限控制

> #### •  登录检查
> - 之前采用拦截器实现了登录检查，这是简单的权限管理方案，现在将其废弃。
> #### • 授权配置
> - 对当前系统内包含的所有的请求，分配访问权限（普通用户、版主、管理员）。
> #### •  认证方案
> - 绕过Security认证流程，采用系统原来的认证方案。
> #### •  CSRF配置
> - 防止 CSRF 攻击的基本原理，以及表单、AJAX相关的配置。

### 2、置顶、加精、删除

> #### •  功能实现
> - 点击 置顶，修改帖子的类型。
> - 点击“加精”、“删除”，修改帖子的状态。
> #### • 权限管理
> - 版主可以执行“置顶”、“加精”操作。
> - 管理员可以执行“删除”操作。
> #### •  按钮显示
> - 版主可以看到“置顶”、“加精”按钮。
> - 管理员可以看到“删除”按钮。